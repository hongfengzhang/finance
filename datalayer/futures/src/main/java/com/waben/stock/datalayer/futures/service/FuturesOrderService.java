package com.waben.stock.datalayer.futures.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.futures.business.FuturesContractBusiness;
import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.datalayer.futures.rabbitmq.RabbitmqConfiguration;
import com.waben.stock.datalayer.futures.rabbitmq.RabbitmqProducer;
import com.waben.stock.datalayer.futures.rabbitmq.message.EntrustQueryMessage;
import com.waben.stock.datalayer.futures.repository.FuturesOrderDao;
import com.waben.stock.interfaces.commonapi.retrivefutures.RetriveFuturesOverHttp;
import com.waben.stock.interfaces.commonapi.retrivefutures.TradeFuturesOverHttp;
import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesContractMarket;
import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesGatewayOrder;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.admin.futures.FuturesTradeAdminDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.FrozenCapitalDto;
import com.waben.stock.interfaces.enums.FuturesActionType;
import com.waben.stock.interfaces.enums.FuturesOrderState;
import com.waben.stock.interfaces.enums.FuturesOrderType;
import com.waben.stock.interfaces.enums.FuturesTradePriceType;
import com.waben.stock.interfaces.enums.FuturesWindControlType;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesTradeAdminQuery;
import com.waben.stock.interfaces.pojo.query.futures.FuturesOrderQuery;
import com.waben.stock.interfaces.util.StringUtil;
import com.waben.stock.interfaces.util.UniqueCodeGenerator;

/**
 * 期货订单 service
 * 
 * @author sunl
 *
 */
@Service
public class FuturesOrderService {

	@Autowired
	private FuturesOrderDao futuresOrderDao;

	@Autowired
	private FuturesContractBusiness futuresContractBusiness;

	@Autowired
	private RabbitmqProducer producer;

	@Value("{gateway.order.domain:}")
	private String domain;

	public FuturesOrder findById(Long id) {
		return futuresOrderDao.retrieve(id);
	}

	public Page<FuturesTradeAdminDto> adminPagesByQuery(FuturesTradeAdminQuery query) {
		String publisherNameCondition = "";
		if (!StringUtil.isEmpty(query.getPublisherName())) {
			publisherNameCondition = " and t4.name like '%" + query.getPublisherName() + "%' ";
		}
		String publisherPhoneCondition = "";
		if (!StringUtil.isEmpty(query.getPublisherPhone())) {
			publisherPhoneCondition = " and t3.phone like '%" + query.getPublisherPhone() + "%' ";
		}
		List<FuturesTradeAdminDto> content = new ArrayList<FuturesTradeAdminDto>();
		BigInteger totalElements = new BigInteger("10");
		return new PageImpl<>(content, new PageRequest(query.getPage(), query.getSize()),
				totalElements != null ? totalElements.longValue() : 0);
	}

	public Page<FuturesOrder> pagesOrder(final FuturesOrderQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<FuturesOrder> pages = futuresOrderDao.page(new Specification<FuturesOrder>() {
			@Override
			public Predicate toPredicate(Root<FuturesOrder> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				// 订单状态
				if (query.getState() != null) {
					predicateList.add(criteriaBuilder.equal(root.get("state").as(Integer.class), query.getState()));
				}
				// 是否测试单
				if (query.getIsTest() != null) {
					Predicate isTestPredicate = criteriaBuilder.equal(root.get("isTest").as(Boolean.class),
							query.getIsTest());
					Predicate isTestNullPredicate = criteriaBuilder.isNull(root.get("isTest").as(Boolean.class));
					if (query.getIsTest()) {
						predicateList.add(isTestPredicate);
					} else {
						predicateList.add(criteriaBuilder.or(isTestPredicate, isTestNullPredicate));
					}
				}
				if (predicateList.size() > 0) {
					criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
				}

				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

	@Transactional
	public FuturesOrder save(FuturesOrder order) {
		CapitalAccountDto capitalAccount = futuresContractBusiness.findByPublisherId(order.getPublisherId());
		BigDecimal totalFee = order.getServiceFee().add(order.getReserveFund());
		if (order.getDeferred()) {
			totalFee = totalFee.add(order.getOvernightPerUnitDeferredFee());
		}
		if (totalFee.compareTo(capitalAccount.getAvailableBalance()) > 0) {
			throw new ServiceException(ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION);
		}

		// 调取行情接口 获取买入最新价
		FuturesContractMarket market = RetriveFuturesOverHttp.market(order.getContractSymbol());
		order.setBuyingPrice(market == null ? new BigDecimal(0) : market.getLastPrice()); // 买入最新价
		order.setTradeNo(UniqueCodeGenerator.generateTradeNo());
		Date date = new Date();
		order.setPostTime(date);
		order.setBuyingTime(date);
		order.setState(FuturesOrderState.Position);
		order = futuresOrderDao.create(order);
		// 扣去金额、冻结保证金
		try {
			futuresContractBusiness.futuresOrderServiceFeeAndReserveFund(order.getPublisherId(), order.getId(),
					order.getServiceFee(), order.getReserveFund(), order.getOvernightPerUnitDeferredFee());
		} catch (ServiceException ex) {
			if (ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION.equals(ex.getType())) {
				throw ex;
			} else {
				// 再一次确认是否已经扣款
				try {
					FrozenCapitalDto frozen = futuresContractBusiness
							.futuresOrderFetchFrozenCapital(order.getPublisherId(), order.getId());
					if (frozen == null) {
						throw ex;
					}
				} catch (ServiceException frozenEx) {
					throw ex;
				}
			}
		}
		BigDecimal entrustPrice = new BigDecimal(0);
		if ((order.getBuyingPriceType().getIndex()).equals("2")) {
			entrustPrice = order.getBuyingEntrustPrice();
		}

		FuturesGatewayOrder gatewayOrder = TradeFuturesOverHttp.placeOrder(domain, order.getContractSymbol(),
				order.getId(), FuturesActionType.BUY, order.getTotalQuantity(),
				Integer.valueOf(order.getBuyingPriceType().getIndex()), entrustPrice);

		if (gatewayOrder != null) {
			order.setState(FuturesOrderState.BuyingEntrust);
			futuresOrderDao.update(order);
		}

		// FuturesOrderEntrust entrust = new FuturesOrderEntrust();
		// entrust.setTradeNo(order.getId().toString());
		// entrust.setOrderId(order.getId());
		// entrust.setContractName(order.getContractName());
		// entrust.setContractSymbol(order.getContractSymbol());
		// entrust.setLossPosition(order.getPerUnitLimitLossPosition());
		// entrust.setOrderType(order.getOrderType());
		// entrust.setProfitPosition(order.getPerUnitLimitProfitPositon());
		// entrust.setState(order.getState());
		// entrust.setTradeNo(order.getTradeNo());
		// producer.voluntarilyEntrustApplyBuyIn(entrust);

		return order;
	}

	public FuturesOrder editOrder(Long id, FuturesOrderState state) {

		return futuresOrderDao.editOrder(id, state);
	}

	public Integer countOrderType(Long contractId, FuturesOrderType orderType) {
		return futuresOrderDao.countOrderByType(contractId, orderType);
	}

	public Integer sumByListOrderContractIdAndPublisherId(Long contractId, Long publisherId) {
		return futuresOrderDao.sumByListOrderContractIdAndPublisherId(contractId, publisherId);
	}

	/**
	 * 已取消
	 * 
	 * @param id
	 *            订单ID
	 * @return 订单
	 */
	@Transactional
	public FuturesOrder cancelOrder(Long id) {
		FuturesOrder order = futuresOrderDao.retrieve(id);
		if (!(order.getState() == FuturesOrderState.Posted || order.getState() == FuturesOrderState.BuyingEntrust)) {
			throw new ServiceException(ExceptionConstant.FUTURESORDER_STATE_NOTMATCH_OPERATION_NOTSUPPORT_EXCEPTION);
		}
		// TODO 撤单退款
		// 修改订单状态
		order.setState(FuturesOrderState.Canceled);
		order.setUpdateTime(new Date());
		futuresOrderDao.update(order);
		// TODO 站外消息推送
		return order;
	}

	/**
	 * 部分买入成功
	 * 
	 * @param id
	 *            订单ID
	 * @return 订单
	 */
	@Transactional
	public FuturesOrder partPositionOrder(Long id) {
		FuturesOrder order = futuresOrderDao.retrieve(id);
		if (!(order.getState() == FuturesOrderState.Posted || order.getState() == FuturesOrderState.BuyingEntrust
				|| order.getState() == FuturesOrderState.PartPosition)) {
			throw new ServiceException(ExceptionConstant.FUTURESORDER_STATE_NOTMATCH_OPERATION_NOTSUPPORT_EXCEPTION);
		}
		// 修改订单状态
		order.setState(FuturesOrderState.PartPosition);
		order.setUpdateTime(new Date());
		return futuresOrderDao.update(order);
	}

	/**
	 * 部分已平仓
	 * 
	 * @param id
	 *            订单ID
	 * @return 订单
	 */
	@Transactional
	public FuturesOrder partUnwindOrder(Long id) {
		FuturesOrder order = futuresOrderDao.retrieve(id);
		if (!(order.getState() == FuturesOrderState.Position || order.getState() == FuturesOrderState.SellingEntrust
				|| order.getState() == FuturesOrderState.PartUnwind)) {
			throw new ServiceException(ExceptionConstant.FUTURESORDER_STATE_NOTMATCH_OPERATION_NOTSUPPORT_EXCEPTION);
		}
		// 修改订单状态
		order.setState(FuturesOrderState.PartUnwind);
		order.setUpdateTime(new Date());
		return futuresOrderDao.update(order);
	}

	/**
	 * 持仓中
	 * 
	 * @param id
	 *            订单ID
	 * @param buyingPrice
	 *            买入价格
	 * @return 订单
	 */
	@Transactional
	public FuturesOrder positionOrder(Long id, BigDecimal buyingPrice) {
		FuturesOrder order = futuresOrderDao.retrieve(id);
		if (!(order.getState() == FuturesOrderState.Posted || order.getState() == FuturesOrderState.BuyingEntrust
				|| order.getState() == FuturesOrderState.PartPosition)) {
			throw new ServiceException(ExceptionConstant.FUTURESORDER_STATE_NOTMATCH_OPERATION_NOTSUPPORT_EXCEPTION);
		}
		// TODO 计算止盈、止损点位
		// 修改订单状态
		Date date = new Date();
		order.setBuyingPrice(buyingPrice);
		order.setBuyingTime(date);
		order.setState(FuturesOrderState.Position);
		order.setUpdateTime(date);
		futuresOrderDao.update(order);
		// TODO 站外消息推送
		return order;
	}

	/**
	 * 已平仓
	 * 
	 * @param id
	 *            订单ID
	 * @param sellingPrice
	 *            卖出价格
	 * @return 订单
	 */
	public FuturesOrder unwindOrder(Long id, BigDecimal sellingPrice) {
		FuturesOrder order = futuresOrderDao.retrieve(id);
		if (!(order.getState() == FuturesOrderState.Position || order.getState() == FuturesOrderState.SellingEntrust
				|| order.getState() == FuturesOrderState.PartUnwind)) {
			throw new ServiceException(ExceptionConstant.FUTURESORDER_STATE_NOTMATCH_OPERATION_NOTSUPPORT_EXCEPTION);
		}
		// TODO 给用户结算
		// 修改订单状态
		Date date = new Date();
		order.setSellingPrice(sellingPrice);
		order.setSellingTime(date);
		order.setState(FuturesOrderState.Unwind);
		order.setUpdateTime(date);
		futuresOrderDao.update(order);
		// TODO 站外消息推送
		return order;
	}

	/**
	 * 卖出委托
	 * 
	 * @param order
	 *            订单
	 * @param windControlType
	 *            风控类型
	 * @param priceType
	 *            价格类型
	 * @param entrustPrice
	 *            委托价格
	 * @return 订单
	 */
	public FuturesOrder sellingEntrust(FuturesOrder order, FuturesWindControlType windControlType,
			FuturesTradePriceType priceType, BigDecimal entrustPrice) {
		if (order.getState() != FuturesOrderState.Position) {
			throw new ServiceException(ExceptionConstant.FUTURESORDER_STATE_NOTMATCH_OPERATION_NOTSUPPORT_EXCEPTION);
		}
		// 修改订单状态
		order.setWindControlType(windControlType);
		order.setState(FuturesOrderState.SellingEntrust);
		order.setUpdateTime(new Date());
		order.setSellingPriceType(priceType);
		order.setSellingEntrustPrice(entrustPrice);
		// 委托卖出
		FuturesActionType action = order.getOrderType() == FuturesOrderType.BuyUp ? FuturesActionType.SELL
				: FuturesActionType.BUY;
		Integer userOrderType = priceType == FuturesTradePriceType.MKT ? 1 : 2;
		FuturesGatewayOrder gatewayOrder = TradeFuturesOverHttp.placeOrder(domain, order.getContractSymbol(),
				order.getId(), action, order.getTotalQuantity(), userOrderType, entrustPrice);
		order.setCloseGatewayOrderId(gatewayOrder.getId());
		// 放入委托查询队列（平仓）
		EntrustQueryMessage msg = new EntrustQueryMessage();
		msg.setEntrustType(3);
		producer.sendMessage(RabbitmqConfiguration.entrustQueryQueueName, msg);
		return futuresOrderDao.update(order);
	}

}
