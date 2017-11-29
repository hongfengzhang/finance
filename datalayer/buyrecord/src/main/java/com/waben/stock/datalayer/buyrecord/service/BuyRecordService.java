package com.waben.stock.datalayer.buyrecord.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.buyrecord.entity.BuyRecord;
import com.waben.stock.datalayer.buyrecord.repository.BuyRecordDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.util.UniqueCodeGenerator;

/**
 * 点买记录 Service
 * 
 * @author luomengan
 *
 */
@Service
public class BuyRecordService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BuyRecordDao buyRecordDao;

	@Transactional
	public BuyRecord save(BuyRecord buyRecord) {
		buyRecord.setSerialCode(UniqueCodeGenerator.generateSerialCode());
		buyRecord.setTradeNo(UniqueCodeGenerator.generateTradeNo());
		buyRecord.setState(BuyRecordState.POSTED);
		buyRecord.setPublisherSelling(false);
		buyRecord.setCreateTime(new Date());
		return buyRecordDao.create(buyRecord);
	}

	public Page<BuyRecord> pagesByQuery(final BuyRecordQuery buyRecordQuery) {
		Pageable pageable = new PageRequest(buyRecordQuery.getPage(), buyRecordQuery.getSize());
		Page<BuyRecord> pages = buyRecordDao.page(new Specification<BuyRecord>() {
			@Override
			public Predicate toPredicate(Root<BuyRecord> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				if (buyRecordQuery.getStates() != null && buyRecordQuery.getStates().length > 0) {
					criteriaQuery
							.where(criteriaBuilder.and(root.get("state").in(buyRecordQuery.getStates()), criteriaBuilder
									.equal(root.get("publisherId").as(Long.class), buyRecordQuery.getPublisherId())));
				}
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

	@Transactional
	public BuyRecord changeState(BuyRecord record) {
		BuyRecordState current = record.getState();
		BuyRecordState next = BuyRecordState.UNKONWN;
		if (BuyRecordState.POSTED.equals(current)) {
			next = BuyRecordState.BUYLOCK;
		} else if (BuyRecordState.BUYLOCK.equals(current)) {
			next = BuyRecordState.HOLDPOSITION;
		} else if (BuyRecordState.HOLDPOSITION.equals(current)) {
			next = BuyRecordState.SELLLOCK;
		} else if (BuyRecordState.SELLLOCK.equals(current)) {
			next = BuyRecordState.UNWIND;
		}
		record.setState(next);
		BuyRecord result = buyRecordDao.update(record);
		if (result.getState().equals(next)) {
			logger.info("点买交易状态更新成功,id:{}", result.getSerialCode());
			if (next.equals(BuyRecordState.HOLDPOSITION)) {
				// 若点买交易记录为持仓中，向消息队列中添加当前点买交易记录

			}
		}
		return result;
	}

	@Transactional
	public void remove(Long buyRecordId) {
		buyRecordDao.delete(buyRecordId);
	}

	@Transactional
	public BuyRecord buyLock(Long investorId, Long id) {
		BuyRecord buyRecord = buyRecordDao.retrieve(id);
		if (buyRecord == null) {
			throw new ServiceException(ExceptionConstant.DATANOTFOUND_EXCEPTION);
		}
		if (buyRecord.getState() != BuyRecordState.POSTED) {
			throw new ServiceException(ExceptionConstant.BUYRECORD_STATE_NOTMATCH_OPERATION_NOTSUPPORT_EXCEPTION);
		}
		buyRecord.setInvestorId(investorId);
		// 修改点买记录状态
		return changeState(buyRecord);
	}

	@Transactional
	public BuyRecord buyInto(Long investorId, Long id, String delegateNumber, BigDecimal buyingPrice,
			Integer numberOfStrand) {
		BuyRecord buyRecord = buyRecordDao.retrieve(id);
		if (buyRecord == null) {
			throw new ServiceException(ExceptionConstant.DATANOTFOUND_EXCEPTION);
		}
		if (buyRecord.getState() != BuyRecordState.BUYLOCK) {
			throw new ServiceException(ExceptionConstant.BUYRECORD_ISNOTLOCK_EXCEPTION);
		}
		if (investorId != buyRecord.getInvestorId()) {
			throw new ServiceException(ExceptionConstant.BUYRECORD_INVESTORID_NOTMATCH_EXCEPTION);
		}
		buyRecord.setDelegateNumber(delegateNumber);
		buyRecord.setBuyingPrice(buyingPrice);
		buyRecord.setBuyingTime(new Date());
		buyRecord.setNumberOfStrand(numberOfStrand);
		// 止盈点位价格 = 买入价格 + ((市值 * 止盈点)/股数)
		buyRecord.setProfitPosition(buyingPrice.add(buyRecord.getApplyAmount().multiply(buyRecord.getProfitPoint())
				.divide(new BigDecimal(numberOfStrand), 2, RoundingMode.HALF_UP)));
		// 止盈预警点位价格 = 买入价格 + ((市值 * (止盈点-0.05))/股数)
		buyRecord.setProfitWarnPosition(buyingPrice
				.add(buyRecord.getApplyAmount().multiply(buyRecord.getProfitPoint().subtract(new BigDecimal(0.05)))
						.divide(new BigDecimal(numberOfStrand), 2, RoundingMode.HALF_UP)));
		// 止损点位价格 = 买入价格 - ((市值 * 止损点)/股数)
		buyRecord.setLossPosition(
				buyingPrice.subtract(buyRecord.getApplyAmount().multiply(buyRecord.getLossPoint().abs())
						.divide(new BigDecimal(numberOfStrand), 2, RoundingMode.HALF_UP)));
		// 止损预警点位价格 = 买入价格 - ((市值 * (止损点+0.05))/股数)
		buyRecord.setLossWarnPosition(buyingPrice.subtract(
				buyRecord.getApplyAmount().multiply(buyRecord.getLossPoint().abs().subtract(new BigDecimal(0.05)))
						.divide(new BigDecimal(numberOfStrand), 2, RoundingMode.HALF_UP)));
		// 修改点买记录状态
		return changeState(buyRecord);
	}

	@Transactional
	public BuyRecord sellLock(Long lockUserId, Long id, Boolean isPublisher) {
		BuyRecord buyRecord = buyRecordDao.retrieve(id);
		if (buyRecord == null) {
			throw new ServiceException(ExceptionConstant.DATANOTFOUND_EXCEPTION);
		}
		if (buyRecord.getState() != BuyRecordState.HOLDPOSITION) {
			throw new ServiceException(ExceptionConstant.BUYRECORD_STATE_NOTMATCH_OPERATION_NOTSUPPORT_EXCEPTION);
		}
		if (isPublisher) {
			if (lockUserId != buyRecord.getPublisherId()) {
				throw new ServiceException(ExceptionConstant.BUYRECORD_PUBLISHERID_NOTMATCH_EXCEPTION);
			}
			buyRecord.setPublisherSelling(true);
		} else {
			if (lockUserId != buyRecord.getInvestorId()) {
				throw new ServiceException(ExceptionConstant.BUYRECORD_INVESTORID_NOTMATCH_EXCEPTION);
			}
		}
		// 修改点买记录状态
		return changeState(buyRecord);
	}

	@Transactional
	public BuyRecord sellOut(Long investorId, Long id, BigDecimal sellingPrice, BigDecimal profitDistributionRatio) {
		BuyRecord buyRecord = buyRecordDao.retrieve(id);
		if (buyRecord.getState() != BuyRecordState.SELLLOCK) {
			throw new ServiceException(ExceptionConstant.BUYRECORD_ISNOTLOCK_EXCEPTION);
		}
		if (investorId != buyRecord.getInvestorId()) {
			throw new ServiceException(ExceptionConstant.BUYRECORD_INVESTORID_NOTMATCH_EXCEPTION);
		}
		buyRecord.setSellingPrice(sellingPrice);
		buyRecord.setSellingTime(new Date());
		BigDecimal profitOrLoss = sellingPrice.subtract(buyRecord.getBuyingPrice())
				.multiply(new BigDecimal(buyRecord.getNumberOfStrand()));
		// 计算发布人分配到的盈亏
		if (profitOrLoss.compareTo(BigDecimal.ZERO) >= 0) {
			buyRecord.setPublisherProfitOrLoss(profitOrLoss.multiply(profitDistributionRatio));
		} else {
			if (profitOrLoss.abs().compareTo(buyRecord.getReserveFund()) > 0) {
				buyRecord.setPublisherProfitOrLoss(buyRecord.getReserveFund().multiply(new BigDecimal(-1)));
			} else {
				buyRecord.setPublisherProfitOrLoss(profitOrLoss);
			}
		}
		buyRecord.setProfitOrLoss(profitOrLoss);
		// 修改点买记录状态
		return changeState(buyRecord);
	}

}
