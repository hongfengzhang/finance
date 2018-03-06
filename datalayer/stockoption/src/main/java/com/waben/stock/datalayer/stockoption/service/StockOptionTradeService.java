package com.waben.stock.datalayer.stockoption.service;

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
import org.springframework.util.StringUtils;

import com.waben.stock.datalayer.stockoption.business.CapitalAccountBusiness;
import com.waben.stock.datalayer.stockoption.business.PublisherBusiness;
import com.waben.stock.datalayer.stockoption.entity.OfflineStockOptionTrade;
import com.waben.stock.datalayer.stockoption.entity.StockOptionTrade;
import com.waben.stock.datalayer.stockoption.repository.OfflineStockOptionTradeDao;
import com.waben.stock.datalayer.stockoption.repository.StockOptionTradeDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.enums.OfflineStockOptionTradeState;
import com.waben.stock.interfaces.enums.StockOptionTradeState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeQuery;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeUserQuery;
import com.waben.stock.interfaces.util.UniqueCodeGenerator;

@Service
public class StockOptionTradeService {

	@Autowired
	private StockOptionTradeDao stockOptionTradeDao;
	@Autowired
	private OfflineStockOptionTradeDao offlineStockOptionTradeDao;
	@Autowired
	private PublisherBusiness publisherBusiness;

	@Autowired
	private CapitalAccountBusiness capitalAccountBusiness;

	public Page<StockOptionTrade> pagesByUserQuery(final StockOptionTradeUserQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<StockOptionTrade> pages = stockOptionTradeDao.page(new Specification<StockOptionTrade>() {
			@Override
			public Predicate toPredicate(Root<StockOptionTrade> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<>();
				if (query.getStates() != null && query.getStates().length > 0) {
					predicateList.add(root.get("state").in(query.getStates()));
				}
				if (query.getPublisherId() != null && query.getPublisherId() > 0) {
					predicateList
							.add(criteriaBuilder.equal(root.get("publisherId").as(Long.class), query.getPublisherId()));
				}
				if (predicateList.size() > 0) {
					criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
				}
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sellingTime").as(Date.class)),
						criteriaBuilder.desc(root.get("buyingTime").as(Date.class)),
						criteriaBuilder.desc(root.get("applyTime").as(Date.class)));
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

	public StockOptionTrade save(StockOptionTrade stockOptionTrade) {
		// 再检查一余额是否充足
		CapitalAccountDto account = capitalAccountBusiness.fetchByPublisherId(stockOptionTrade.getPublisherId());
		if (account.getAvailableBalance().compareTo(stockOptionTrade.getRightMoney()) < 0) {
			throw new ServiceException(ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION);
		}
		stockOptionTrade.setTradeNo(UniqueCodeGenerator.generateTradeNo());
		stockOptionTrade.setState(StockOptionTradeState.WAITCONFIRMED);
		Date date = new Date();
		stockOptionTrade.setApplyTime(date);
		stockOptionTrade.setUpdateTime(date);
		stockOptionTradeDao.create(stockOptionTrade);
		// 扣去权利金
		try {
			// TODO
		} catch (ServiceException ex) {
			if (ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION.equals(ex.getType())) {
				throw ex;
			} else {
				// 再一次确认是否已经扣款
				try {
					// TODO
				} catch (ServiceException frozenEx) {
					throw ex;
				}
			}
		}
		// TODO 站外消息推送
		return stockOptionTrade;
	}
	
	public StockOptionTrade userRight(Long publisherId, Long id) {
		StockOptionTrade trade = findById(id);
		if (trade.getState() != StockOptionTradeState.TURNOVER) {
			throw new ServiceException(ExceptionConstant.STOCKOPTION_STATE_NOTMATCH_OPERATION_NOTSUPPORT_EXCEPTION);
		}
		if (!trade.getPublisherId().equals(publisherId)) {
			throw new ServiceException(ExceptionConstant.STOCKOPTION_PUBLISHERID_NOTMATCH_EXCEPTION);
		}
		trade.setRightTime(new Date());
		trade.setState(StockOptionTradeState.APPLYRIGHT);
		stockOptionTradeDao.update(trade);
		// TODO 发送站外消息
		return trade;
	}

	public Page<StockOptionTrade> pagesByQuery(final StockOptionTradeQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<StockOptionTrade> pages = stockOptionTradeDao.page(new Specification<StockOptionTrade>() {
			@Override
			public Predicate toPredicate(Root<StockOptionTrade> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicatesList = new ArrayList();
				if (query.getBeginTime() != null) {
					predicatesList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("buyingTime").as(Date.class),
							query.getBeginTime()));
				}
				if (query.getEndTime() != null) {
					predicatesList
							.add(criteriaBuilder.lessThan(root.get("buyingTime").as(Date.class), query.getEndTime()));
				}
				if (!StringUtils.isEmpty(query.getPublisherPhone())) {
					Predicate publisherPhoneQuery = criteriaBuilder.equal(root.get("publisherPhone").as(Long.class),
							query.getPublisherPhone());
					predicatesList.add(publisherPhoneQuery);
				}
				if (!StringUtils.isEmpty(query.getApplyNo())) {
					Predicate applyNoQuery = criteriaBuilder.equal(root.get("applyNo").as(String.class),
							query.getApplyNo());
					predicatesList.add(applyNoQuery);
				}
				if (!StringUtils.isEmpty(query.getState())) {
					Predicate stateQuery = criteriaBuilder.equal(root.get("state").as(Integer.class), query.getState());
					predicatesList.add(stateQuery);
				}
				criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

	public StockOptionTrade findById(Long id) {
		return stockOptionTradeDao.retrieve(id);
	}

    public StockOptionTrade settlement(Long id) {
        StockOptionTrade stockOptionTrade = stockOptionTradeDao.retrieve(id);
        CapitalAccountDto capitalAccountDto = capitalAccountBusiness.fetchByPublisherId(stockOptionTrade.getPublisherId());
        //TODO 给用户结算
        //修改订单状态
        return stockOptionTrade;
    }

    public StockOptionTrade success(Long id) {
		StockOptionTrade stockOptionTrade = stockOptionTradeDao.retrieve(id);
		if(!StockOptionTradeState.WAITCONFIRMED.equals(stockOptionTrade.getState())) {
			throw new ServiceException(ExceptionConstant.BUYRECORD_STATE_NOTMATCH_OPERATION_NOTSUPPORT_EXCEPTION);
		}
		stockOptionTrade.setState(StockOptionTradeState.TURNOVER);
		StockOptionTrade result = stockOptionTradeDao.update(stockOptionTrade);
		return result;
    }

    @Transactional
	public StockOptionTrade exercise(Long id) {
		StockOptionTrade stockOptionTrade = stockOptionTradeDao.retrieve(id);
		OfflineStockOptionTrade offlineStockOptionTrade = offlineStockOptionTradeDao.retrieve(id);
		stockOptionTrade.setState(StockOptionTradeState.INSETTLEMENT);
		offlineStockOptionTrade.setState(OfflineStockOptionTradeState.APPLYRIGHT);
		offlineStockOptionTradeDao.update(offlineStockOptionTrade);
		StockOptionTrade result = stockOptionTradeDao.update(stockOptionTrade);
		return result;
	}

    public StockOptionTrade fail(Long id) {
		StockOptionTrade stockOptionTrade = stockOptionTradeDao.retrieve(id);
		if(!StockOptionTradeState.WAITCONFIRMED.equals(stockOptionTrade.getState())) {
			throw new ServiceException(ExceptionConstant.BUYRECORD_STATE_NOTMATCH_OPERATION_NOTSUPPORT_EXCEPTION);
		}
		stockOptionTrade.setState(StockOptionTradeState.FAILURE);
		StockOptionTrade result = stockOptionTradeDao.update(stockOptionTrade);
		return result;
    }

}
