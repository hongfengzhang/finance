package com.waben.stock.datalayer.futures.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.futures.business.FuturesContractBusiness;
import com.waben.stock.datalayer.futures.entity.FuturesOrder;
import com.waben.stock.datalayer.futures.repository.FuturesOrderDao;
import com.waben.stock.datalayer.futures.warpper.rabbit.FuturesOrderProducer;
import com.waben.stock.interfaces.commonapi.retrivefutures.RetriveFuturesOverHttp;
import com.waben.stock.interfaces.commonapi.retrivefutures.bean.FuturesContractMarket;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.FrozenCapitalDto;
import com.waben.stock.interfaces.enums.FuturesOrderState;
import com.waben.stock.interfaces.enums.FuturesOrderType;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.query.futures.FuturesOrderQuery;
import com.waben.stock.interfaces.pojo.stock.FuturesOrderEntrust;
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
	private FuturesOrderProducer producer;

	public Page<FuturesOrder> pagesOrder(final FuturesOrderQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<FuturesOrder> pages = futuresOrderDao.page(new Specification<FuturesOrder>() {

			@Override
			public Predicate toPredicate(Root<FuturesOrder> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				// Join<FuturesExchange, FuturesContract> parentJoin =
				// root.join("exchange", JoinType.LEFT);

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
		order.setBuyingPrice(market.getLastPrice()); // 买入最新价
		order.setTradeNo(UniqueCodeGenerator.generateTradeNo());
		Date date = new Date();
		order.setPostTime(date);
		order.setBuyingTime(date);
		order.setState(FuturesOrderState.Position);
		futuresOrderDao.create(order);
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

		FuturesOrderEntrust entrust = new FuturesOrderEntrust();
		entrust.setTradeNo(order.getId().toString());
		entrust.setOrderId(order.getId());
		entrust.setContractName(order.getContractName());
		entrust.setContractSymbol(order.getContractSymbol());
		entrust.setLossPosition(order.getPerUnitLimitLossPosition());
		entrust.setOrderType(order.getOrderType());
		entrust.setProfitPosition(order.getPerUnitLimitProfitPositon());
		entrust.setState(order.getState());
		entrust.setTradeNo(order.getTradeNo());
		producer.voluntarilyEntrustApplyBuyIn(entrust);

		return order;
	}

	public FuturesOrder editOrder(Long id, FuturesOrderState state) {

		return futuresOrderDao.editOrder(id, state);
	}

	public Integer countOrderType(Long contractId, FuturesOrderType orderType) {
		return futuresOrderDao.countOrderByType(contractId, orderType);
	}

	public BigDecimal sumByListOrderContractIdAndPublisherId(Long contractId, Long publisherId) {
		return futuresOrderDao.sumByListOrderContractIdAndPublisherId(contractId, publisherId);
	}
}
