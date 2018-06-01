package com.waben.stock.futuresgateway.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import com.ib.client.Order;
import com.ib.client.OrderType;
import com.waben.stock.futuresgateway.dao.FuturesContractDao;
import com.waben.stock.futuresgateway.dao.FuturesOrderDao;
import com.waben.stock.futuresgateway.entity.FuturesContract;
import com.waben.stock.futuresgateway.entity.FuturesOrder;
import com.waben.stock.futuresgateway.exception.ExceptionEnum;
import com.waben.stock.futuresgateway.exception.ServiceException;
import com.waben.stock.futuresgateway.twsapi.TwsEngine;

/**
 * 期货订单 Service
 * 
 * @author luomengan
 *
 */
@Service
public class FuturesOrderService {

	@Autowired
	private FuturesOrderDao futuresOrderDao;

	@Autowired
	private FuturesContractDao futuresContractDao;

	@Autowired
	private TwsEngine twsEngine;

	public FuturesOrder getFuturesOrderInfo(Long id) {
		return futuresOrderDao.retrieveFuturesOrderById(id);
	}

	@Transactional
	public FuturesOrder modifyFuturesOrder(FuturesOrder futuresOrder) {
		return futuresOrderDao.updateFuturesOrder(futuresOrder);
	}

	@Transactional
	public void deleteFuturesOrder(Long id) {
		futuresOrderDao.deleteFuturesOrderById(id);
	}

	@Transactional
	public void deleteFuturesOrders(String ids) {
		if (ids != null) {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				if (!"".equals(id.trim())) {
					futuresOrderDao.deleteFuturesOrderById(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<FuturesOrder> futuresOrders(int page, int limit) {
		return futuresOrderDao.pageFuturesOrder(page, limit);
	}

	public List<FuturesOrder> list() {
		return futuresOrderDao.listFuturesOrder();
	}

	public FuturesOrder getFuturesOrderInfoByTwsOrderId(int twsOrderId) {
		return futuresOrderDao.retrieveFuturesOrderByTwsOrderId(twsOrderId);
	}

	/**
	 * 取消订单
	 * 
	 * @param domain
	 *            应用域
	 * @param outerOrderId
	 *            外部订单ID
	 */
	@Transactional
	public void cancelOrder(String domain, Long outerOrderId) {
		EClientSocket client = twsEngine.getClient();
		
	}

	/**
	 * 下单
	 * 
	 * @param domain
	 *            应用域
	 * @param symbol
	 *            合约标识
	 * @param outerOrderId
	 *            外部订单ID
	 * @param action
	 *            交易方向
	 * @param totalQuantity
	 *            交易总量
	 * @param userOrderType
	 *            用户订单类型
	 * @param entrustPrice
	 *            委托价格
	 * @return 订单
	 */
	@Transactional
	public synchronized FuturesOrder addFuturesOrder(String domain, String symbol, Integer outerOrderId, String action,
			BigDecimal totalQuantity, Integer userOrderType, BigDecimal entrustPrice) {
		FuturesContract futuresContract = futuresContractDao.retrieveContractBySymbol(symbol);
		if (futuresContract == null) {
			throw new ServiceException(ExceptionEnum.Symbol_NotSuported);
		}
		if (!("BUY".equals(action) || "SELL".equals(action))) {
			throw new ServiceException(ExceptionEnum.Action_NotSuported);
		}
		if (!((userOrderType != null && userOrderType == 1) || (userOrderType != null && userOrderType == 2))) {
			throw new ServiceException(ExceptionEnum.UserOrderType_NotSuported);
		}
		// step 1 : 保存订单
		FuturesOrder futuresOrder = new FuturesOrder();
		futuresOrder.setContractId(futuresContract.getId());
		futuresOrder.setCurrency(futuresContract.getCurrency());
		futuresOrder.setAction(action);
		futuresOrder.setUserOrderType(userOrderType);
		futuresOrder.setOuterOrderId(outerOrderId);
		futuresOrder.setTotalQuantity(totalQuantity);
		futuresOrder.setEntrustPrice(entrustPrice);
		futuresOrder.setStatus("Init");
		Integer twsOrderId = twsEngine.getWrapper().getCurrentOrderId();
		twsEngine.getWrapper().setCurrentOrderId(twsOrderId + 1);
		String account = twsEngine.getAccount();
		futuresOrder.setAccount(account);
		futuresOrder.setTwsOrderId(twsOrderId);
		futuresOrder.setCreateTime(new Date());
		// step 2 : 请求tws下单
		EClientSocket client = twsEngine.getClient();
		Order order = new Order();
		order.action(futuresOrder.getAction());
		order.totalQuantity(futuresOrder.getTotalQuantity().doubleValue());
		order.account(account);
		if (futuresOrder.getUserOrderType() == 2) {
			order.orderType(OrderType.LMT);
			order.lmtPrice(futuresOrder.getEntrustPrice().doubleValue());
			futuresOrder.setTwsOrderType(OrderType.LMT.toString());
		} else {
			order.orderType(OrderType.MKT);
			futuresOrder.setTwsOrderType(OrderType.MKT.toString());
		}
		Contract contract = new Contract();
		contract.localSymbol(futuresContract.getLocalSymbolName());
		contract.secType(futuresContract.getSecType());
		contract.currency(futuresContract.getCurrency());
		contract.exchange(futuresContract.getExchange());
		client.placeOrder(twsOrderId, contract, order);
		return futuresOrderDao.createFuturesOrder(futuresOrder);
	}

}
