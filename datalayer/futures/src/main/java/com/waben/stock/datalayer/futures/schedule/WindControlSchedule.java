package com.waben.stock.datalayer.futures.schedule;

import java.math.BigDecimal;
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

import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesContractTerm;
import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.datalayer.futures.entity.FuturesOvernightRecord;
import com.waben.stock.datalayer.futures.service.FuturesOrderService;
import com.waben.stock.datalayer.futures.service.FuturesOvernightRecordService;
import com.waben.stock.interfaces.commonapi.retrivefutures.RetriveFuturesOverHttp;
import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesContractMarket;
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
				// step 2 : 遍历所有订单，判断是否达到风控平仓条件
				if (content != null && content.size() > 0) {
					for (FuturesOrder order : content) {
						FuturesContractTerm term = order.getContractTerm();
						Integer timeZoneGap = retriveTimeZoneGap(order);
						// step 3 : 是否合约到期
						if (isTradeTime(timeZoneGap, term) && isReachContractExpiration(timeZoneGap, term)) {
							orderService.sellingEntrust(order, FuturesWindControlType.ReachContractExpiration,
									FuturesTradePriceType.MKT, null);
							continue;
						}
						// step 4 : 获取合约行情
						FuturesContractMarket market = RetriveFuturesOverHttp.market(order.getContractSymbol());
						// step 5 : 是否达到止盈点
						if (isTradeTime(timeZoneGap, term) && isReachProfitPoint(order, market)) {
							orderService.sellingEntrust(order, FuturesWindControlType.ReachProfitPoint,
									FuturesTradePriceType.MKT, null);
							continue;
						}
						// step 6 : 是否达到止损点
						if (isTradeTime(timeZoneGap, term) && isReachLossPoint(order, market)) {
							orderService.sellingEntrust(order, FuturesWindControlType.ReachLossPoint,
									FuturesTradePriceType.MKT, null);
							continue;
						}
						// step 7 : 是否触发隔夜时间
						if (isTradeTime(timeZoneGap, term) && isTriggerOvernight(order)) {
							// orderService.
							continue;
						}
					}
				}
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
		Date localTime = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(localTime);
		cal.add(Calendar.HOUR_OF_DAY, timeZoneGap);
		return cal.getTime();
	}

	/**
	 * 是否在交易时间
	 * 
	 * @param contract
	 *            期货合约
	 * @return 是否在交易时间
	 */
	private boolean isTradeTime(Integer timeZoneGap, FuturesContractTerm term) {
		if (term != null) {
			Date exchangeTime = retriveExchangeTime(timeZoneGap);
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
		if (orderType == FuturesOrderType.BuyUp) {
			if (lastPrice != null && lastPrice.compareTo(order.getLimitProfitPrice()) >= 0) {
				return true;
			}
		} else {
			if (lastPrice != null && lastPrice.compareTo(order.getLimitLossPrice()) <= 0) {
				return true;
			}
		}
		return false;
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
		if (orderType == FuturesOrderType.BuyUp) {
			if (lastPrice != null && lastPrice.compareTo(order.getLimitLossPrice()) <= 0) {
				return true;
			}
		} else {
			if (lastPrice != null && lastPrice.compareTo(order.getLimitLossPrice()) >= 0) {
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
	private boolean isTriggerOvernight(FuturesOrder order) {
		FuturesOvernightRecord record = overnightService.findNewestOvernightRecord(order);
		Date now = new Date();
		String nowStr = daySdf.format(now);
		// 判断是否有今天的隔夜记录
		if (!(record != null && nowStr.equals(daySdf.format(record.getDeferredTime())))) {
			FuturesContract contract = order.getContract();
			String overnightTime = contract.getOvernightTime();
			try {
				// 判断是否达到隔夜时间，隔夜时间~隔夜时间+5分钟
				Date beginTime = daySdf.parse(nowStr + " " + overnightTime);
				Date endTime = new Date(beginTime.getTime() + 5 * 60 * 1000);
				if (now.getTime() >= beginTime.getTime() && now.getTime() < endTime.getTime()) {
					return true;
				}
			} catch (ParseException e) {
				logger.error("期货合约" + contract.getSymbol() + "隔夜时间格式错误?" + overnightTime);
			}
		}
		return false;
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
