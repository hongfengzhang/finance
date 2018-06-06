package com.waben.stock.applayer.tactics.business.futures;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.tactics.dto.futures.FuturesOrderMarketDto;
import com.waben.stock.interfaces.commonapi.retrivefutures.RetriveFuturesOverHttp;
import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesContractMarket;
import com.waben.stock.interfaces.dto.futures.FuturesContractDto;
import com.waben.stock.interfaces.dto.futures.FuturesCurrencyRateDto;
import com.waben.stock.interfaces.dto.futures.FuturesOrderDto;
import com.waben.stock.interfaces.dto.futures.TurnoverStatistyRecordDto;
import com.waben.stock.interfaces.enums.FuturesOrderState;
import com.waben.stock.interfaces.enums.FuturesOrderType;
import com.waben.stock.interfaces.enums.FuturesTradePriceType;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.futures.FuturesOrderQuery;
import com.waben.stock.interfaces.service.futures.FuturesContractInterface;
import com.waben.stock.interfaces.service.futures.FuturesCurrencyRateInterface;
import com.waben.stock.interfaces.service.futures.FuturesOrderInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;

@Service
public class FuturesOrderBusiness {

	@Autowired
	@Qualifier("futuresOrderInterface")
	private FuturesOrderInterface futuresOrderInterface;

	@Autowired
	@Qualifier("futuresCurrencyRateInterface")
	private FuturesCurrencyRateInterface futuresCurrencyRateInterface;

	@Autowired
	@Qualifier("futurescontractInterface")
	private FuturesContractInterface futuresContractInterface;

	public Integer sumUserNum(Long contractId, Long publisherId) {
		Response<Integer> response = futuresOrderInterface.sumByListOrderContractIdAndPublisherId(contractId,
				publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public FuturesOrderDto applyUnwind(Long orderId, FuturesTradePriceType sellingPriceType,
			BigDecimal sellingEntrustPrice) {
		Response<FuturesOrderDto> response = futuresOrderInterface.applyUnwind(orderId, sellingPriceType.getIndex(),
				sellingEntrustPrice);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public FuturesOrderDto backhandUnwind(Long orderId) {
		Response<FuturesOrderDto> response = futuresOrderInterface.backhandUnwind(orderId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public void applyUnwindAll(Long publisherId) {
		Response<Void> response = futuresOrderInterface.applyUnwindAll(publisherId);
		if ("200".equals(response.getCode())) {
			return;
		}
		throw new ServiceException(response.getCode());
	}

	public FuturesOrderDto cancelOrder(Long orderId) {
		Response<FuturesOrderDto> response = futuresOrderInterface.cancelOrder(orderId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public FuturesOrderDto buy(FuturesOrderDto orderDto) {
		Response<FuturesOrderDto> response = futuresOrderInterface.addOrder(orderDto);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PageInfo<FuturesOrderDto> pageOrder(FuturesOrderQuery orderQuery) {
		Response<PageInfo<FuturesOrderDto>> response = futuresOrderInterface.pagesOrder(orderQuery);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public PageInfo<FuturesOrderMarketDto> pageOrderMarket(FuturesOrderQuery orderQuery) {
		PageInfo<FuturesOrderDto> pageOrder = pageOrder(orderQuery);
		List<FuturesOrderMarketDto> orderMarketList = CopyBeanUtils.copyListBeanPropertiesToList(pageOrder.getContent(),
				FuturesOrderMarketDto.class);
		orderMarketList = getListFuturesOrders(orderMarketList);
		return new PageInfo<>(orderMarketList, pageOrder.getTotalPages(), pageOrder.getLast(),
				pageOrder.getTotalElements(), pageOrder.getSize(), pageOrder.getNumber(), pageOrder.getFrist());
	}

	public FuturesCurrencyRateDto findByCurrency(String currency) {
		Response<FuturesCurrencyRateDto> response = futuresCurrencyRateInterface.findByCurrency(currency);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public FuturesContractDto findByContractId(Long contractId) {
		Response<FuturesContractDto> response = futuresContractInterface.findByContractId(contractId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	/**
	 * 计算订单止损止盈及用户盈亏
	 * 
	 * @param orderList
	 *            订单数据
	 * @return 订单列表
	 */
	public List<FuturesOrderMarketDto> getListFuturesOrders(List<FuturesOrderMarketDto> orderList) {
		if (orderList != null && orderList.size() > 0) {
			for (FuturesOrderMarketDto orderMarket : orderList) {
				if (orderMarket.getOrderType() == FuturesOrderType.BuyUp) {
					orderMarket.setBuyOrderTypeDesc(
							"买涨" + Integer.valueOf(orderMarket.getTotalQuantity().intValue()) + "手");
				} else {
					orderMarket.setBuyOrderTypeDesc(
							"买跌" + Integer.valueOf(orderMarket.getTotalQuantity().intValue()) + "手");
				}
				// 获取合约信息
				FuturesContractDto contract = findByContractId(orderMarket.getContractId());
				if (contract == null) {
					break;
				}
				// 获取汇率信息
				FuturesCurrencyRateDto rate = findByCurrency(orderMarket.getContractCurrency());
				orderMarket.setRate(rate.getRate());
				orderMarket.setCurrencySign(rate.getCurrencySign());
				// 买入价
				BigDecimal buyingPrice = new BigDecimal(0);
				if (orderMarket.getBuyingPriceType() == FuturesTradePriceType.MKT) {
					buyingPrice = orderMarket.getBuyingPrice() == null ? new BigDecimal(0)
							: orderMarket.getBuyingPrice();
				} else {
					buyingPrice = orderMarket.getBuyingEntrustPrice() == null ? new BigDecimal(0)
							: orderMarket.getBuyingEntrustPrice();
				}
				// 止盈
				if (orderMarket.getLimitProfitType() != null && orderMarket.getPerUnitLimitProfitAmount() != null) {
					// 按用户设置价格计算止盈金额
					if (orderMarket.getLimitProfitType() == 1) {
						// | 止盈金额 = （设置价格 - 买入价）/ 最小波动点位 * 波动一次盈亏金额 * 汇率 |
						orderMarket.setPerUnitLimitProfitPositon(orderMarket.getPerUnitLimitProfitAmount()
								.subtract(buyingPrice).divide(contract.getMinWave())
								.multiply(contract.getPerWaveMoney()).multiply(rate.getRate()).abs());
					} else {
						orderMarket.setPerUnitLimitProfitPositon(orderMarket.getPerUnitLimitProfitAmount());
					}
				}
				// 止损
				if (orderMarket.getLimitLossType() != null && orderMarket.getPerUnitLimitLossAmount() != null) {
					// 按用户设置价格计算止损金额
					if (orderMarket.getLimitLossType() == 1) {
						// 止损金额 = （设置价格 - 买入价）/ 最小波动点位 * 波动一次盈亏金额 * 汇率
						orderMarket.setPerUnitLimitLossPosition(orderMarket.getPerUnitLimitProfitAmount()
								.subtract(buyingPrice).divide(contract.getMinWave())
								.multiply(contract.getPerWaveMoney()).multiply(rate.getRate()));
					} else {
						orderMarket.setPerUnitLimitLossPosition(orderMarket.getPerUnitLimitLossAmount());
					}
				}
				// 订单结算状态为 已取消或委托失败时 不计算用户盈亏
				if (orderMarket.getState() != FuturesOrderState.BuyingCanceled
						&& orderMarket.getState() != FuturesOrderState.BuyingFailure) {
					// 获取行情信息
					FuturesContractMarket market = RetriveFuturesOverHttp.market(orderMarket.getContractSymbol());
					if (market == null) {
						break;
					}
					orderMarket.setPerWaveMoney(contract.getPerWaveMoney());
					orderMarket.setMinWave(contract.getMinWave());
					orderMarket.setLastPrice(market.getLastPrice());
					// 用户盈亏 = （最新价 - 买入价） / 最小波动点 * 波动一次盈亏金额 * 汇率
					orderMarket.setFloatingProfitOrLoss(
							market.getLastPrice().subtract(buyingPrice).divide(contract.getMinWave())
									.multiply(contract.getPerWaveMoney()).multiply(rate.getRate()));
				}

			}
		}
		return orderList;
	}

	public Integer settingStopLoss(Long orderId, Integer limitProfitType, BigDecimal perUnitLimitProfitAmount,
			Integer limitLossType, BigDecimal perUnitLimitLossAmount) {
		Response<FuturesOrderDto> response = futuresOrderInterface.settingStopLoss(orderId, limitProfitType,
				perUnitLimitProfitAmount, limitLossType, perUnitLimitLossAmount);
		if ("200".equals(response.getCode())) {
			return 1;
		}
		throw new ServiceException(response.getCode());
	}

	public TurnoverStatistyRecordDto getTurnoverStatistyRecord() {
		Response<TurnoverStatistyRecordDto> response = futuresOrderInterface.getTurnoverStatisty();
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
