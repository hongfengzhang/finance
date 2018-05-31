package com.waben.stock.datalayer.futures.schedule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.waben.stock.datalayer.futures.business.CapitalAccountBusiness;
import com.waben.stock.datalayer.futures.business.CapitalFlowBusiness;
import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesContractTerm;
import com.waben.stock.datalayer.futures.entity.FuturesCurrencyRate;
import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.datalayer.futures.entity.FuturesOvernightRecord;
import com.waben.stock.datalayer.futures.service.FuturesCurrencyRateService;
import com.waben.stock.datalayer.futures.service.FuturesOrderService;
import com.waben.stock.datalayer.futures.service.FuturesOvernightRecordService;
import com.waben.stock.interfaces.commonapi.retrivefutures.RetriveFuturesOverHttp;
import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesContractMarket;
import com.waben.stock.interfaces.dto.publisher.CapitalFlowDto;
import com.waben.stock.interfaces.enums.CapitalFlowExtendType;
import com.waben.stock.interfaces.enums.CapitalFlowType;
import com.waben.stock.interfaces.enums.FuturesOrderState;
import com.waben.stock.interfaces.enums.FuturesOrderType;
import com.waben.stock.interfaces.enums.FuturesTradePriceType;
import com.waben.stock.interfaces.enums.FuturesWindControlType;
import com.waben.stock.interfaces.pojo.query.futures.FuturesOrderQuery;
import com.waben.stock.interfaces.util.StringUtil;

/**
 * 风控作业
 * 
 * @author luomengan
 *
 */
@Component
public class WindControlSchedule {

	/**
	 * 监控间隔
	 * <p>
	 * 如果是工作日，每间隔5秒中获取持仓中的股票，判断持仓中的股票
	 * </p>
	 */
	public static final long Execute_Interval = 5 * 1000;

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FuturesOrderService orderService;

	@Autowired
	private FuturesOvernightRecordService overnightService;

	@Autowired
	private CapitalAccountBusiness accountBusiness;

	@Autowired
	private FuturesCurrencyRateService rateService;

	@Autowired
	private CapitalFlowBusiness flowBusiness;

	private SimpleDateFormat daySdf = new SimpleDateFormat("yyyy-MM-dd");

	private SimpleDateFormat fullSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@PostConstruct
	public void initTask() {
		Timer timer = new Timer();
		timer.schedule(new WindControlTask(), Execute_Interval);
	}

	private class WindControlTask extends TimerTask {
		@Override
		public void run() {
			try {
				// step 1 : 获取所有持仓中的正式单
				List<FuturesOrder> content = retrivePositionOrders();
				logger.info("监控持仓中的正式单数量：" + content.size());
				// step 2 : 遍历所有订单，判断是否达到风控平仓条件
				if (content != null && content.size() > 0) {
					for (FuturesOrder order : content) {
						FuturesContractTerm term = order.getContractTerm();
						Integer timeZoneGap = retriveTimeZoneGap(order);
						// step 3 : 是否触发退还隔夜保证金时间
						checkAndDoReturnOvernightReserveFund(order, timeZoneGap, term);
						// step 4 : 是否合约到期
						if (isTradeTime(timeZoneGap, term) && isReachContractExpiration(timeZoneGap, term)) {
							orderService.sellingEntrust(order, FuturesWindControlType.ReachContractExpiration,
									FuturesTradePriceType.MKT, null);
							continue;
						}
						// step 5 : 获取合约行情
						FuturesContractMarket market = RetriveFuturesOverHttp.market(order.getContractSymbol());
						// step 6 : 是否达到止盈点
						if (isTradeTime(timeZoneGap, term) && isReachProfitPoint(order, market)) {
							orderService.sellingEntrust(order, FuturesWindControlType.ReachProfitPoint,
									FuturesTradePriceType.MKT, null);
							continue;
						}
						// step 7 : 是否达到止损点
						if (isTradeTime(timeZoneGap, term) && isReachLossPoint(order, market)) {
							orderService.sellingEntrust(order, FuturesWindControlType.ReachLossPoint,
									FuturesTradePriceType.MKT, null);
							continue;
						}
						// step 8 : 是否触发隔夜时间
						if (isTradeTime(timeZoneGap, term) && isTriggerOvernight(order, timeZoneGap)) {
							orderService.overnight(order);
							continue;
						}
					}
				}
			} catch (Exception ex) {
				logger.error("监控持仓订单发生异常!", ex);
			} finally {
				initTask();
			}
		}
	}

	/**
	 * 获取北京时间和交易所的时差
	 * 
	 * @param order
	 *            订单
	 * @return 北京时间和交易所的时差
	 */
	private Integer retriveTimeZoneGap(FuturesOrder order) {
		return order.getContract().getExchange().getTimeZoneGap();
	}

	/**
	 * 获取交易所的对应时间
	 * 
	 * @param timeZoneGap
	 *            和交易所的时差
	 * @return 交易所的对应时间
	 */
	private Date retriveExchangeTime(Integer timeZoneGap) {
		return retriveExchangeTime(new Date(), timeZoneGap);
	}

	/**
	 * 获取交易所的对应时间
	 * 
	 * @param localTime
	 *            日期
	 * @param timeZoneGap
	 *            和交易所的时差
	 * @return 交易所的对应时间
	 */
	private Date retriveExchangeTime(Date localTime, Integer timeZoneGap) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(localTime);
		cal.add(Calendar.HOUR_OF_DAY, timeZoneGap);
		return cal.getTime();
	}

	/**
	 * 是否在交易时间
	 * 
	 * @param timeZoneGap
	 *            时区
	 * @param term
	 *            合约期限
	 * @return 是否在交易时间
	 */
	private boolean isTradeTime(Integer timeZoneGap, FuturesContractTerm term) {
		return isTradeTime(timeZoneGap, term, new Date());
	}

	/**
	 * 是否在交易时间
	 * 
	 * @param timeZoneGap
	 *            时区
	 * @param term
	 *            合约期限
	 * @param date
	 *            日期
	 * @return 是否在交易时间
	 */
	private boolean isTradeTime(Integer timeZoneGap, FuturesContractTerm term, Date date) {
		if (term != null) {
			Date exchangeTime = retriveExchangeTime(date, timeZoneGap);
			Calendar cal = Calendar.getInstance();
			cal.setTime(exchangeTime);
			int week = cal.get(Calendar.DAY_OF_WEEK);
			String tradeTime = null;
			if (week == 1) {
				tradeTime = term.getSunTradeTime();
			} else if (week == 2) {
				tradeTime = term.getMonTradeTime();
			} else if (week == 3) {
				tradeTime = term.getTueTradeTime();
			} else if (week == 4) {
				tradeTime = term.getWedTradeTime();
			} else if (week == 5) {
				tradeTime = term.getThuTradeTime();
			} else if (week == 6) {
				tradeTime = term.getFriTradeTime();
			} else if (week == 7) {
				tradeTime = term.getSatTradeTime();
			}
			if (!StringUtil.isEmpty(tradeTime)) {
				String[] tradeTimeArr = tradeTime.split(",");
				boolean isTradeTime = false;
				for (String tradeTimeDuration : tradeTimeArr) {
					String[] tradeTimePointArr = tradeTimeDuration.trim().split("-");
					String dayStr = daySdf.format(exchangeTime);
					String fullStr = fullSdf.format(exchangeTime);
					if (fullStr.compareTo(dayStr + " " + tradeTimePointArr[0].trim()) >= 0
							&& fullStr.compareTo(dayStr + " " + tradeTimePointArr[1].trim()) < 0) {
						isTradeTime = true;
					}
				}
				return isTradeTime;
			}
		}
		return true;
	}

	/**
	 * 判断是否合约到期
	 * 
	 * @param order
	 *            订单
	 * @return 是否合约到期
	 */
	private boolean isReachContractExpiration(Integer timeZoneGap, FuturesContractTerm term) {
		if (term != null) {
			Date exchangeTime = retriveExchangeTime(timeZoneGap);
			Date forceUnwindDate = term.getForceUnwindDate();
			if (forceUnwindDate != null && exchangeTime.getTime() >= forceUnwindDate.getTime()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 计算订单止盈价格
	 * 
	 * @param order
	 *            订单
	 * @return 止盈价格
	 */
	private BigDecimal computeLimitProfitPrice(FuturesOrder order) {
		FuturesOrderType orderType = order.getOrderType();
		BigDecimal buyingPrice = order.getBuyingPrice();
		// 用户设置
		Integer limitProfitType = order.getLimitProfitType();
		BigDecimal perUnitLimitProfitAmount = order.getPerUnitLimitProfitAmount();
		// 波动设置
		BigDecimal minWave = order.getContract().getMinWave();
		BigDecimal perWaveMoney = order.getContract().getPerWaveMoney();
		// 货币汇率
		FuturesCurrencyRate rate = rateService.findByCurrency(order.getContractCurrency());
		if (buyingPrice != null && perUnitLimitProfitAmount != null) {
			if (limitProfitType != null && limitProfitType == 1) {
				// type为行情价格
				if (orderType == FuturesOrderType.BuyUp && perUnitLimitProfitAmount.compareTo(buyingPrice) > 0) {
					return perUnitLimitProfitAmount;
				} else if (orderType == FuturesOrderType.BuyFall
						&& perUnitLimitProfitAmount.compareTo(buyingPrice) < 0) {
					return perUnitLimitProfitAmount;
				}
			} else if (limitProfitType != null && limitProfitType == 2 && rate != null && rate.getRate() != null) {
				// type为每手盈利金额
				BigDecimal needWavePrice = (perUnitLimitProfitAmount.divide(rate.getRate(), 2, RoundingMode.DOWN)
						.divide(perWaveMoney, 2, RoundingMode.DOWN).multiply(minWave)
						.setScale(minWave.scale(), RoundingMode.DOWN));
				if (orderType == FuturesOrderType.BuyUp) {
					return buyingPrice.add(needWavePrice);
				} else if (orderType == FuturesOrderType.BuyFall) {
					return buyingPrice.subtract(needWavePrice);
				}
			}
		}
		return null;
	}

	/**
	 * 判断是否达到止盈点
	 * 
	 * @param order
	 *            订单
	 * @param market
	 *            行情
	 * @return 否达到止盈点
	 */
	private boolean isReachProfitPoint(FuturesOrder order, FuturesContractMarket market) {
		BigDecimal lastPrice = market.getLastPrice();
		FuturesOrderType orderType = order.getOrderType();
		BigDecimal limitProfitPrice = computeLimitProfitPrice(order);
		if (orderType == FuturesOrderType.BuyUp) {
			if (lastPrice != null && limitProfitPrice != null && lastPrice.compareTo(limitProfitPrice) >= 0) {
				return true;
			}
		} else {
			if (lastPrice != null && limitProfitPrice != null && lastPrice.compareTo(limitProfitPrice) <= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 计算订单止损价格
	 * 
	 * @param order
	 *            订单
	 * @return 止损价格
	 */
	private BigDecimal computeLimitLossPrice(FuturesOrder order) {
		FuturesOrderType orderType = order.getOrderType();
		BigDecimal buyingPrice = order.getBuyingPrice();
		// 合约设置
		Integer unwindPointType = order.getUnwindPointType();
		BigDecimal perUnitUnwindPoint = order.getPerUnitUnwindPoint();
		// 用户设置
		Integer limitLossType = order.getLimitLossType();
		BigDecimal perUnitLimitLossAmount = order.getPerUnitLimitLossAmount();
		// 波动设置
		BigDecimal minWave = order.getContract().getMinWave();
		BigDecimal perWaveMoney = order.getContract().getPerWaveMoney();
		// 货币汇率
		FuturesCurrencyRate rate = rateService.findByCurrency(order.getContractCurrency());
		if (buyingPrice != null) {
			// 获取合约设置的止损价格
			BigDecimal contractSetNeedWavePrice = null;
			if (unwindPointType != null && perUnitUnwindPoint != null && unwindPointType == 1) {
				if (perUnitUnwindPoint != null && perUnitUnwindPoint.compareTo(new BigDecimal(1)) < 0
						&& perUnitUnwindPoint.compareTo(new BigDecimal(0)) > 0) {
					contractSetNeedWavePrice = (order.getReserveFund().divide(order.getTotalQuantity())
							.multiply(new BigDecimal(1).subtract(perUnitUnwindPoint))
							.divide(rate.getRate(), 2, RoundingMode.DOWN).divide(perWaveMoney, 2, RoundingMode.DOWN)
							.multiply(minWave).setScale(minWave.scale(), RoundingMode.DOWN));
				}
			} else if (unwindPointType != null && perUnitUnwindPoint != null && unwindPointType == 2) {
				if (perUnitUnwindPoint != null && perUnitUnwindPoint.compareTo(BigDecimal.ZERO) >= 0
						&& perUnitUnwindPoint.compareTo(new BigDecimal(0)) > 0) {
					contractSetNeedWavePrice = (order.getReserveFund().divide(order.getTotalQuantity())
							.subtract(perUnitUnwindPoint).divide(rate.getRate(), 2, RoundingMode.DOWN)
							.divide(perWaveMoney, 2, RoundingMode.DOWN).multiply(minWave)
							.setScale(minWave.scale(), RoundingMode.DOWN));
				}
			}
			// 获取用户设置的止损价格
			BigDecimal userSetNeedWavePrice = null;
			if (limitLossType != null && limitLossType == 1) {
				// type为行情价格
				if (orderType == FuturesOrderType.BuyUp && perUnitLimitLossAmount.compareTo(buyingPrice) < 0) {
					userSetNeedWavePrice = buyingPrice.subtract(perUnitLimitLossAmount);
				} else if (orderType == FuturesOrderType.BuyFall && perUnitLimitLossAmount.compareTo(buyingPrice) > 0) {
					userSetNeedWavePrice = perUnitLimitLossAmount.subtract(buyingPrice);
				}
			} else if (limitLossType != null && limitLossType == 2 && rate != null && rate.getRate() != null) {
				// type为每手亏损剩余到金额
				userSetNeedWavePrice = (order.getReserveFund().divide(order.getTotalQuantity())
						.subtract(perUnitLimitLossAmount).divide(rate.getRate(), 2, RoundingMode.DOWN)
						.divide(perWaveMoney, 2, RoundingMode.DOWN).multiply(minWave)
						.setScale(minWave.scale(), RoundingMode.DOWN));
			}
			// 获取最终需要波动的价格
			BigDecimal lastNeedWavePrice = null;
			if (contractSetNeedWavePrice != null && userSetNeedWavePrice == null) {
				lastNeedWavePrice = contractSetNeedWavePrice;
			} else if (contractSetNeedWavePrice == null && userSetNeedWavePrice != null) {
				lastNeedWavePrice = userSetNeedWavePrice;
			} else {
				lastNeedWavePrice = contractSetNeedWavePrice.abs().compareTo(userSetNeedWavePrice.abs()) > 0
						? contractSetNeedWavePrice : userSetNeedWavePrice;
			}
			if (lastNeedWavePrice != null) {
				if (orderType == FuturesOrderType.BuyUp) {
					return buyingPrice.subtract(lastNeedWavePrice.abs());
				} else if (orderType == FuturesOrderType.BuyFall) {
					return buyingPrice.add(lastNeedWavePrice.abs());
				}
			}
		}
		return null;
	}

	/**
	 * 判断是否达到止损点
	 * 
	 * @param order
	 *            订单
	 * @param market
	 *            行情
	 * @return 是否达到止损点
	 */
	private boolean isReachLossPoint(FuturesOrder order, FuturesContractMarket market) {
		BigDecimal lastPrice = market.getLastPrice();
		FuturesOrderType orderType = order.getOrderType();
		BigDecimal limitLossPrice = computeLimitLossPrice(order);
		if (orderType == FuturesOrderType.BuyUp) {
			if (lastPrice != null && limitLossPrice != null && lastPrice.compareTo(limitLossPrice) <= 0) {
				return true;
			}
		} else {
			if (lastPrice != null && limitLossPrice != null && lastPrice.compareTo(limitLossPrice) >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否触发隔夜
	 * 
	 * @param order
	 *            订单
	 * @return 是否触发隔夜
	 */
	private boolean isTriggerOvernight(FuturesOrder order, Integer timeZoneGap) {
		FuturesOvernightRecord record = overnightService.findNewestOvernightRecord(order);
		Date now = new Date();
		Date nowExchangeTime = retriveExchangeTime(now, timeZoneGap);
		String nowStr = daySdf.format(nowExchangeTime);
		// 判断是否有今天的隔夜记录
		if (!(record != null && nowStr.equals(daySdf.format(record.getDeferredTime())))) {
			FuturesContract contract = order.getContract();
			String overnightTime = contract.getOvernightTime();
			try {
				// 判断是否达到隔夜时间，隔夜时间~隔夜时间+1分钟
				Date beginTime = daySdf.parse(nowStr + " " + overnightTime);
				Date endTime = new Date(beginTime.getTime() + 1 * 60 * 1000);
				if (nowExchangeTime.getTime() >= beginTime.getTime() && nowExchangeTime.getTime() < endTime.getTime()) {
					return true;
				}
			} catch (ParseException e) {
				logger.error("期货合约" + contract.getSymbol() + "隔夜时间格式错误?" + overnightTime);
			}
		}
		return false;
	}

	/**
	 * 判断是否触发退还隔夜保证金
	 * 
	 * @param order
	 *            订单
	 * @return 是否触发隔夜
	 */
	private void checkAndDoReturnOvernightReserveFund(FuturesOrder order, Integer timeZoneGap,
			FuturesContractTerm term) {
		// 判断当前时候+30分钟是否为交易时间段
		Date now = new Date();
		Date nowAfter30mins = new Date(now.getTime() + 30 * 60 * 1000);
		boolean isTradeTime = isTradeTime(timeZoneGap, term, nowAfter30mins);
		if (!isTradeTime) {
			return;
		}
		// 获取退还隔夜保证金的时间
		FuturesContract contract = order.getContract();
		String returnOvernightReserveFundTime = contract.getReturnOvernightReserveFundTime();
		Date nowExchangeTime = retriveExchangeTime(now, timeZoneGap);
		String nowStr = daySdf.format(nowExchangeTime);
		try {
			// 判断是否到达退还隔夜保证金时间，退还隔夜保证金时间~退还隔夜保证金时间+1分钟
			Date beginTime = daySdf.parse(nowStr + " " + returnOvernightReserveFundTime);
			Date endTime = new Date(beginTime.getTime() + 1 * 60 * 1000);
			if (nowExchangeTime.getTime() >= beginTime.getTime() && nowExchangeTime.getTime() < endTime.getTime()) {
				// 如果到达退还隔夜保证金时间
				FuturesOvernightRecord record = overnightService.findNewestOvernightRecord(order);
				if (record != null) {
					List<CapitalFlowDto> flowList = flowBusiness
							.fetchByExtendTypeAndExtendId(CapitalFlowExtendType.FUTURESOVERNIGHTRECORD, record.getId());
					if (flowList != null && flowList.size() > 0) {
						boolean hasOvernightReserveFund = false;
						boolean hasReturnOvernightReserveFund = false;
						BigDecimal reserveFund = BigDecimal.ZERO;
						for (CapitalFlowDto flow : flowList) {
							if (flow.getType() == CapitalFlowType.FuturesOvernightReserveFund) {
								hasOvernightReserveFund = true;
								reserveFund = flow.getAmount();
							}
							if (flow.getType() == CapitalFlowType.FuturesReturnOvernightReserveFund) {
								hasReturnOvernightReserveFund = true;
							}
						}
						if (hasOvernightReserveFund && !hasReturnOvernightReserveFund) {
							// 退还隔夜保证金
							accountBusiness.futuresReturnOvernightReserveFund(order.getId(), record.getId(),
									reserveFund);
						}
					}
				}
			}
		} catch (ParseException e) {
			logger.error("期货合约" + contract.getSymbol() + "隔夜时间格式错误?" + returnOvernightReserveFundTime);
		}
	}

	/**
	 * 获取所有持仓中的正式单
	 * 
	 * @return 持仓中的正式订单
	 */
	private List<FuturesOrder> retrivePositionOrders() {
		FuturesOrderQuery query = new FuturesOrderQuery();
		query.setPage(0);
		query.setSize(Integer.MAX_VALUE);
		query.setState(FuturesOrderState.Position);
		query.setIsTest(false);
		Page<FuturesOrder> pages = orderService.pagesOrder(query);
		return pages.getContent();
	}

}