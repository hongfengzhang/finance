package com.waben.stock.datalayer.futures.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.futures.business.CapitalAccountBusiness;
import com.waben.stock.datalayer.futures.business.CapitalFlowBusiness;
import com.waben.stock.datalayer.futures.business.OutsideMessageBusiness;
import com.waben.stock.datalayer.futures.entity.FuturesContract;
import com.waben.stock.datalayer.futures.entity.FuturesContractTerm;
import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.datalayer.futures.entity.FuturesOvernightRecord;
import com.waben.stock.datalayer.futures.rabbitmq.RabbitmqConfiguration;
import com.waben.stock.datalayer.futures.rabbitmq.RabbitmqProducer;
import com.waben.stock.datalayer.futures.rabbitmq.message.EntrustQueryMessage;
import com.waben.stock.datalayer.futures.repository.DynamicQuerySqlDao;
import com.waben.stock.datalayer.futures.repository.FuturesContractDao;
import com.waben.stock.datalayer.futures.repository.FuturesContractTermDao;
import com.waben.stock.datalayer.futures.repository.FuturesOrderDao;
import com.waben.stock.datalayer.futures.repository.FuturesOvernightRecordDao;
import com.waben.stock.datalayer.futures.repository.impl.MethodDesc;
import com.waben.stock.interfaces.commonapi.retrivefutures.TradeFuturesOverHttp;
import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesGatewayOrder;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.admin.futures.FuturesOrderAdminDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.CapitalFlowDto;
import com.waben.stock.interfaces.dto.publisher.FrozenCapitalDto;
import com.waben.stock.interfaces.enums.CapitalFlowExtendType;
import com.waben.stock.interfaces.enums.FuturesActionType;
import com.waben.stock.interfaces.enums.FuturesOrderState;
import com.waben.stock.interfaces.enums.FuturesOrderType;
import com.waben.stock.interfaces.enums.FuturesTradePriceType;
import com.waben.stock.interfaces.enums.FuturesWindControlType;
import com.waben.stock.interfaces.enums.OutsideMessageType;
import com.waben.stock.interfaces.enums.ResourceType;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.message.OutsideMessage;
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

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DynamicQuerySqlDao sqlDao;

	@Autowired
	private FuturesOrderDao orderDao;

	@Autowired
	private FuturesContractDao contractDao;

	@Autowired
	private FuturesCurrencyRateService rateService;

	@Autowired
	private FuturesContractTermDao termDao;

	@Autowired
	private FuturesOvernightRecordDao recordDao;

	@Autowired
	private CapitalAccountBusiness accountBusiness;

	@Autowired
	private CapitalFlowBusiness flowBusiness;

	@Autowired
	private OutsideMessageBusiness outsideMessageBusiness;

	@Autowired
	private RabbitmqProducer producer;

	@Value("{order.domain:youguwang.com.cn}")
	private String domain;

	public FuturesOrder findById(Long id) {
		return orderDao.retrieve(id);
	}

	public Page<FuturesOrderAdminDto> adminPagesByQuery(FuturesTradeAdminQuery query) {
		String publisherNameCondition = "";
		if (!StringUtil.isEmpty(query.getPublisherName())) {
			publisherNameCondition = " and f2.phone like '%" + query.getPublisherPhone() + "%' ";
		}
		String publisherPhoneCondition = "";
		if (!StringUtil.isEmpty(query.getPublisherPhone())) {
			publisherPhoneCondition = " and f3.name like '%" + query.getPublisherName() + "%' ";
		}
		String contractNameCondition = "";
		if (!StringUtil.isEmpty(query.getName())) {
			contractNameCondition = " and f4.name like '%" + query.getName() + "%'";
		}
		String orderTypeCondition = "";
		if (!StringUtil.isEmpty(query.getOrderType())) {
			orderTypeCondition = " and f1.orderType like '%" + query.getOrderType() + "%'";
		}
		String orderStateCondition = "";
		if (!StringUtil.isEmpty(query.getOrderState())) {
			orderStateCondition = " and f1.state like '%" + query.getOrderState() + "%'";
		}
		String sql = String.format(
				"select f1.id, f3.name,f2.phone,f4.name as cname,f1.trade_no, f1.open_gateway_order_id, f1.close_gateway_order_id, f1.order_type, f1.state, f1.total_quantity, f1.buying_time,"
						+ " f1.buying_price, f1.profit_or_loss, f1.openwind_service_fee, f1.reserve_fund, f1.per_unit_limit_profit_amount, f1.per_unit_limit_loss_amount,"
						+ " f1.selling_time, f1.selling_price, f1.unwind_service_fee, f1.wind_control_type"
						+ " from f_futures_order f1 " + " LEFT JOIN publisher f2 on f1.publisher_id = f2.id"
						+ " LEFT JOIN real_name f3 on f1.publisher_id = f3.resource_id"
						+ " LEFT JOIN f_futures_contract f4 ON f1.contract_id = f4.id"
						+ " where 1=1 %s %s %s %s %s ORDER BY f1.post_time LIMIT " + query.getPage() * query.getSize()
						+ "," + query.getSize(),
				publisherNameCondition, publisherPhoneCondition, contractNameCondition, orderStateCondition,
				orderTypeCondition);
		String countSql = "select count(*) " + sql.substring(sql.indexOf("from"), sql.indexOf("LIMIT"));
		Map<Integer, MethodDesc> setMethodMap = new HashMap<>();
		setMethodMap.put(new Integer(0), new MethodDesc("setId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(1), new MethodDesc("setPublisherName", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(2), new MethodDesc("setPublisherPhone", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(3), new MethodDesc("setName", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(4), new MethodDesc("setTradeNo", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(5), new MethodDesc("setOpenGatewayOrderId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(6), new MethodDesc("setCloseGatewayOrderId", new Class<?>[] { Long.class }));
		setMethodMap.put(new Integer(7), new MethodDesc("setOrderType", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(8), new MethodDesc("setState", new Class<?>[] { String.class }));
		setMethodMap.put(new Integer(9), new MethodDesc("setTotalQuantity", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(10), new MethodDesc("setBuyingTime", new Class<?>[] { Date.class }));
		setMethodMap.put(new Integer(11), new MethodDesc("setBuyingPrice", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(12), new MethodDesc("setProfit", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(13), new MethodDesc("setOpenwindServiceFee", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(14), new MethodDesc("setReserveFund", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(15),
				new MethodDesc("setPerUnitLimitProfitAmount", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(16),
				new MethodDesc("setPerUnitLimitLossAmount", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(17), new MethodDesc("setSellingTime", new Class<?>[] { Date.class }));
		setMethodMap.put(new Integer(18), new MethodDesc("setSellingPrice", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(19), new MethodDesc("setUnwindServiceFee", new Class<?>[] { BigDecimal.class }));
		setMethodMap.put(new Integer(20), new MethodDesc("setWindControlType", new Class<?>[] { String.class }));
		List<FuturesOrderAdminDto> content = sqlDao.execute(FuturesOrderAdminDto.class, sql, setMethodMap);
		BigInteger totalElements = sqlDao.executeComputeSql(countSql);
		return new PageImpl<>(content, new PageRequest(query.getPage(), query.getSize()),
				totalElements != null ? totalElements.longValue() : 0);
	}

	public Page<FuturesOrder> pagesOrder(final FuturesOrderQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<FuturesOrder> pages = orderDao.page(new Specification<FuturesOrder>() {
			@Override
			public Predicate toPredicate(Root<FuturesOrder> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				// 用户ID
				if (query.getPublisherId() != null && query.getPublisherId() != 0) {
					predicateList
							.add(criteriaBuilder.equal(root.get("publisherId").as(Long.class), query.getPublisherId()));
				}
				// 订单状态
				if (query.getStates() != null && query.getStates().length > 0) {
					predicateList.add(root.get("state").in(query.getStates()));
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
	public FuturesOrder save(FuturesOrder order, Long contractId) {
		// step 1 : 再一次确认余额是否充足
		CapitalAccountDto capitalAccount = accountBusiness.fetchByPublisherId(order.getPublisherId());
		BigDecimal totalFee = order.getServiceFee().add(order.getReserveFund());
		if (totalFee.compareTo(capitalAccount.getAvailableBalance()) > 0) {
			throw new ServiceException(ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION);
		}
		// step 2 : 获取期货合约和期货合约期限
		FuturesContract contract = contractDao.retrieve(contractId);
		FuturesContractTerm term = null;
		List<FuturesContractTerm> termList = termDao.retrieveByContractAndCurrent(contract, true);
		if (termList != null && termList.size() > 0) {
			term = termList.get(0);
		} else {
			throw new ServiceException(ExceptionConstant.CONTRACTTERM_NOTAVAILABLE_EXCEPTION);
		}
		// step 3 : 初始化订单
		order.setTradeNo(UniqueCodeGenerator.generateTradeNo());
		Date date = new Date();
		order.setPostTime(date);
		order.setUpdateTime(date);
		order.setState(FuturesOrderState.Posted);
		order.setContract(contract);
		order.setContractTerm(term);
		order = orderDao.create(order);
		// step 4 : 扣去金额、冻结保证金
		try {
			accountBusiness.futuresOrderServiceFeeAndReserveFund(order.getPublisherId(), order.getId(),
					order.getServiceFee(), order.getReserveFund());
		} catch (ServiceException ex) {
			if (ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION.equals(ex.getType())) {
				throw ex;
			} else {
				// 再一次确认是否已经扣款
				try {
					FrozenCapitalDto frozen = accountBusiness.futuresOrderFetchFrozenCapital(order.getPublisherId(),
							order.getId());
					if (frozen == null) {
						throw ex;
					}
				} catch (ServiceException frozenEx) {
					throw ex;
				}
			}
		}
		// step 5 : 调用期货网关委托下单
		FuturesActionType action = order.getOrderType() == FuturesOrderType.BuyUp ? FuturesActionType.BUY
				: FuturesActionType.SELL;
		Integer userOrderType = order.getBuyingPriceType() == FuturesTradePriceType.MKT ? 1 : 2;
		FuturesGatewayOrder gatewayOrder = TradeFuturesOverHttp.placeOrder(domain, order.getContractSymbol(),
				order.getId(), action, order.getTotalQuantity(), userOrderType, order.getBuyingEntrustPrice());
		// TODO 委托下单异常情况处理，此处默认为所有的委托都能成功
		// step 6 : 更新订单状态
		order.setState(FuturesOrderState.BuyingEntrust);
		order.setOpenGatewayOrderId(gatewayOrder.getId());
		order = orderDao.update(order);
		// step 7 : 放入委托查询队列（开仓）
		EntrustQueryMessage msg = new EntrustQueryMessage();
		msg.setOrderId(order.getId());
		msg.setGatewayOrderId(gatewayOrder.getId());
		msg.setEntrustType(1);
		producer.sendMessage(RabbitmqConfiguration.entrustQueryQueueName, msg);
		// step 8 : 站外消息推送
		sendOutsideMessage(order);
		return order;
	}

	private void sendOutsideMessage(FuturesOrder order) {
		try {
			FuturesOrderState state = order.getState();
			Map<String, String> extras = new HashMap<>();
			OutsideMessage message = new OutsideMessage();
			message.setPublisherId(order.getPublisherId());
			message.setTitle("期货订单通知");
			extras.put("title", message.getTitle());
			extras.put("publisherId", String.valueOf(order.getPublisherId()));
			extras.put("resourceType", ResourceType.FUTURESORDER.getIndex());
			extras.put("resourceId", String.valueOf(order.getId()));
			message.setExtras(extras);
			switch (state) {
			case BuyingEntrust:
				message.setContent(String.format("您所购买的“%s”期货已进入“买入委托”状态", order.getContractName()));
				extras.put("content",
						String.format("您所购买的“<span id=\"futures\">%s</span>”期货已进入“买入委托”状态", order.getContractName()));
				extras.put("type", OutsideMessageType.FUTURES_BUYINGENTRUST.getIndex());
				break;
			case BuyingCanceled:
				message.setContent(String.format("您所购买的“%s”期货已进入“取消买入委托”状态", order.getContractName()));
				extras.put("content",
						String.format("您所购买的“<span id=\"futures\">%s</span>”期货已进入“已取消”状态", order.getContractName()));
				extras.put("type", OutsideMessageType.FUTURES_CANCELED.getIndex());
				break;
			case Position:
				message.setContent(String.format("您所购买的“%s”期货已进入“持仓中”状态", order.getContractName()));
				extras.put("content",
						String.format("您所购买的“<span id=\"futures\">%s</span>”期货已进入“持仓中”状态", order.getContractName()));
				extras.put("type", OutsideMessageType.FUTURES_POSITION.getIndex());
				break;
			case SellingEntrust:
				message.setContent(String.format("您所购买的“%s”期货已进入“卖出委托”状态", order.getContractName()));
				extras.put("content",
						String.format("您所购买的“<span id=\"futures\">%s</span>”期货已进入“卖出委托”状态", order.getContractName()));
				extras.put("type", OutsideMessageType.FUTURES_SELLINGENTRUST.getIndex());
				break;
			case Unwind:
				message.setContent(String.format("您所购买的“%s”期货已进入“已平仓”状态", order.getContractName()));
				extras.put("content",
						String.format("您所购买的“<span id=\"futures\">%s</span>”期货已进入“已平仓”状态", order.getContractName()));
				extras.put("type", OutsideMessageType.FUTURES_UNWIND.getIndex());
				break;
			default:
				break;
			}
			if (message.getContent() != null) {
				outsideMessageBusiness.send(message);
			}
		} catch (Exception ex) {
			logger.error("发送期货订单通知失败，{}_{}_{}", order.getId(), order.getState(), ex.getMessage());
		}
	}

	public FuturesOrder editOrder(Long id, FuturesOrderState state) {

		return orderDao.editOrder(id, state);
	}

	public Integer countOrderType(Long contractId, FuturesOrderType orderType) {
		return orderDao.countOrderByType(contractId, orderType);
	}

	public Integer sumByListOrderContractIdAndPublisherId(Long contractId, Long publisherId) {
		return orderDao.sumByListOrderContractIdAndPublisherId(contractId, publisherId);
	}

	/**
	 * 订单已取消
	 * 
	 * @param id
	 *            订单ID
	 * @return 订单
	 */
	@Transactional
	public FuturesOrder canceledOrder(Long id) {
		FuturesOrder order = orderDao.retrieve(id);
		if (!(order.getState() == FuturesOrderState.Posted || order.getState() == FuturesOrderState.BuyingEntrust)) {
			throw new ServiceException(ExceptionConstant.FUTURESORDER_STATE_NOTMATCH_EXCEPTION);
		}
		// 撤单退款
		accountBusiness.futuresOrderRevoke(order.getPublisherId(), order.getId(), order.getServiceFee());
		// 修改订单状态
		order.setState(FuturesOrderState.BuyingCanceled);
		order.setUpdateTime(new Date());
		orderDao.update(order);
		// 站外消息推送
		sendOutsideMessage(order);
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
		FuturesOrder order = orderDao.retrieve(id);
		if (!(order.getState() == FuturesOrderState.Posted || order.getState() == FuturesOrderState.BuyingEntrust
				|| order.getState() == FuturesOrderState.PartPosition)) {
			throw new ServiceException(ExceptionConstant.FUTURESORDER_STATE_NOTMATCH_EXCEPTION);
		}
		// 修改订单状态
		order.setState(FuturesOrderState.PartPosition);
		order.setUpdateTime(new Date());
		return orderDao.update(order);
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
		FuturesOrder order = orderDao.retrieve(id);
		if (!(order.getState() == FuturesOrderState.Position || order.getState() == FuturesOrderState.SellingEntrust
				|| order.getState() == FuturesOrderState.PartUnwind)) {
			throw new ServiceException(ExceptionConstant.FUTURESORDER_STATE_NOTMATCH_EXCEPTION);
		}
		// 修改订单状态
		order.setState(FuturesOrderState.PartUnwind);
		order.setUpdateTime(new Date());
		return orderDao.update(order);
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
		FuturesOrder order = orderDao.retrieve(id);
		if (!(order.getState() == FuturesOrderState.Posted || order.getState() == FuturesOrderState.BuyingEntrust
				|| order.getState() == FuturesOrderState.PartPosition)) {
			throw new ServiceException(ExceptionConstant.FUTURESORDER_STATE_NOTMATCH_EXCEPTION);
		}
		// 修改订单状态
		Date date = new Date();
		order.setBuyingPrice(buyingPrice);
		order.setBuyingTime(date);
		order.setState(FuturesOrderState.Position);
		order.setUpdateTime(date);
		orderDao.update(order);
		// 站外消息推送
		sendOutsideMessage(order);
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
		FuturesOrder order = orderDao.retrieve(id);
		if (!(order.getState() == FuturesOrderState.Position || order.getState() == FuturesOrderState.SellingEntrust
				|| order.getState() == FuturesOrderState.PartUnwind)) {
			throw new ServiceException(ExceptionConstant.FUTURESORDER_STATE_NOTMATCH_EXCEPTION);
		}
		// 盈亏（交易所货币）
		BigDecimal currencyProfitOrLoss = computeProfitOrLoss(order.getOrderType(), order.getTotalQuantity(),
				order.getBuyingPrice(), sellingPrice, order.getContract().getMinWave(),
				order.getContract().getPerWaveMoney());
		// 盈亏（人民币）
		BigDecimal rate = rateService.findByCurrency(order.getContractCurrency()).getRate();
		BigDecimal profitOrLoss = currencyProfitOrLoss.multiply(rate).setScale(2, RoundingMode.DOWN);
		// 给用户结算
		CapitalAccountDto account = accountBusiness.futuresOrderSettlement(order.getPublisherId(), order.getId(),
				profitOrLoss);
		// 发布人盈亏（人民币）、平台盈亏（人民币）
		BigDecimal publisherProfitOrLoss = BigDecimal.ZERO;
		BigDecimal platformProfitOrLoss = BigDecimal.ZERO;
		if (profitOrLoss.compareTo(BigDecimal.ZERO) > 0) {
			publisherProfitOrLoss = profitOrLoss;
		} else if (profitOrLoss.compareTo(BigDecimal.ZERO) < 0) {
			publisherProfitOrLoss = account.getRealProfitOrLoss();
			if (profitOrLoss.abs().compareTo(publisherProfitOrLoss.abs()) > 0) {
				platformProfitOrLoss = profitOrLoss.abs().subtract(publisherProfitOrLoss.abs())
						.multiply(new BigDecimal(-1));
			}
		}
		// 修改订单状态
		Date date = new Date();
		order.setCurrencyProfitOrLoss(currencyProfitOrLoss);
		order.setProfitOrLoss(profitOrLoss);
		order.setPublisherProfitOrLoss(publisherProfitOrLoss);
		order.setPlatformProfitOrLoss(platformProfitOrLoss);
		order.setSettlementRate(rate);
		order.setSellingPrice(sellingPrice);
		order.setSellingTime(date);
		order.setState(FuturesOrderState.Unwind);
		order.setUpdateTime(date);
		orderDao.update(order);
		// 站外消息推送
		sendOutsideMessage(order);
		return order;
	}

	/**
	 * 计算盈利或者亏损（交易所货币）
	 * 
	 * @param orderType
	 *            订单类型
	 * @param buyingPrice
	 *            买入价格
	 * @param sellingPrice
	 *            卖出价格
	 * @param minWave
	 *            最小波动
	 * @param perWaveMoney
	 *            波动一次盈亏金额，单位为该合约的货币单位
	 * @param rate
	 *            汇率
	 * @return 盈利或者亏损值
	 */
	private BigDecimal computeProfitOrLoss(FuturesOrderType orderType, BigDecimal totalQuantity, BigDecimal buyingPrice,
			BigDecimal sellingPrice, BigDecimal minWave, BigDecimal perWaveMoney) {
		BigDecimal waveMoney = sellingPrice.subtract(buyingPrice).divide(minWave).setScale(4, RoundingMode.DOWN)
				.multiply(perWaveMoney).multiply(totalQuantity);
		if (orderType == FuturesOrderType.BuyUp) {
			return waveMoney;
		} else {
			return waveMoney.multiply(new BigDecimal(-1));
		}
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
	@Transactional
	public FuturesOrder sellingEntrust(FuturesOrder order, FuturesWindControlType windControlType,
			FuturesTradePriceType priceType, BigDecimal entrustPrice) {
		if (order.getState() != FuturesOrderState.Position) {
			throw new ServiceException(ExceptionConstant.FUTURESORDER_STATE_NOTMATCH_EXCEPTION);
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
		// TODO 委托下单异常情况处理，此处默认为所有的委托都能成功
		// 放入委托查询队列（平仓）
		EntrustQueryMessage msg = new EntrustQueryMessage();
		if (windControlType == FuturesWindControlType.BackhandUnind) {
			msg.setEntrustType(2);
		} else {
			msg.setEntrustType(3);
		}
		msg.setOrderId(order.getId());
		msg.setGatewayOrderId(gatewayOrder.getId());
		producer.sendMessage(RabbitmqConfiguration.entrustQueryQueueName, msg);
		// 消息推送
		sendOutsideMessage(order);
		return orderDao.update(order);
	}

	/**
	 * 隔夜
	 * 
	 * @param order
	 *            订单
	 * @return 订单
	 */
	@Transactional
	public FuturesOrder overnight(FuturesOrder order) {
		if (order.getState() != FuturesOrderState.Position) {
			throw new ServiceException(ExceptionConstant.FUTURESORDER_STATE_NOTMATCH_EXCEPTION);
		}
		// step 1 : 检查余额是否充足
		CapitalAccountDto account = accountBusiness.fetchByPublisherId(order.getPublisherId());
		BigDecimal deferredFee = order.getOvernightPerUnitDeferredFee().multiply(order.getTotalQuantity());
		BigDecimal reserveFund = order.getOvernightPerUnitReserveFund().multiply(order.getTotalQuantity());
		BigDecimal totalFee = deferredFee.add(reserveFund);
		if (account.getAvailableBalance().compareTo(totalFee) < 0) {
			// step 1.1 : 余额不足，强制平仓
			return sellingEntrust(order, FuturesWindControlType.DayUnwind, FuturesTradePriceType.MKT, null);
		} else {
			// step 2 : 保存隔夜记录
			FuturesOvernightRecord overnightRecord = new FuturesOvernightRecord();
			overnightRecord.setOrder(order);
			overnightRecord.setOvernightDeferredFee(deferredFee);
			overnightRecord.setOvernightReserveFund(reserveFund);
			overnightRecord.setPublisherId(order.getPublisherId());
			overnightRecord.setReduceTime(new Date());
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			overnightRecord.setDeferredTime(cal.getTime());
			overnightRecord = recordDao.create(overnightRecord);
			// step 4 : 修改订单状态
			order.setWindControlType(FuturesWindControlType.OvernightPosition);
			order.setUpdateTime(new Date());
			orderDao.update(order);
			// step 5 : 扣除隔夜递延费、冻结隔夜保证金
			if (deferredFee.compareTo(BigDecimal.ZERO) > 0 || reserveFund.compareTo(BigDecimal.ZERO) > 0) {
				try {
					accountBusiness.futuresOrderOvernight(order.getPublisherId(), overnightRecord.getId(), deferredFee,
							reserveFund);
				} catch (ServiceException ex) {
					if (ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION.equals(ex.getType())) {
						// step 1.1 : 余额不足，强制平仓
						order = sellingEntrust(order, FuturesWindControlType.DayUnwind, FuturesTradePriceType.MKT,
								null);
						orderDao.delete(overnightRecord.getId());
					} else {
						// 再一次确认是否已经扣款
						try {
							List<CapitalFlowDto> list = flowBusiness.fetchByExtendTypeAndExtendId(
									CapitalFlowExtendType.FUTURESOVERNIGHTRECORD, overnightRecord.getId());
							if (list == null || list.size() == 0) {
								throw ex;
							}
						} catch (ServiceException frozenEx) {
							throw ex;
						}
					}
				}
			}
		}
		return order;
	}
}
