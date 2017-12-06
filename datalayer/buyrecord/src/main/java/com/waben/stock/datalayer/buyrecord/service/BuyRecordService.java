package com.waben.stock.datalayer.buyrecord.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.waben.stock.datalayer.buyrecord.entity.Settlement;
import com.waben.stock.datalayer.buyrecord.repository.BuyRecordDao;
import com.waben.stock.datalayer.buyrecord.repository.SettlementDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.enums.WindControlType;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.util.UniqueCodeGenerator;

/**
 * 点买记录 Service
 *
 * @author luomengan
 */
@Service
public class BuyRecordService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BuyRecordDao buyRecordDao;

	@Autowired
	private SettlementDao settlementDao;

	@Autowired
	private CapitalAccountService accountService;

	public BuyRecord findBuyRecord(Long buyrecord) {
		BuyRecord buyRecord = buyRecordDao.retrieve(buyrecord);
		if (buyRecord == null) {
			throw new ServiceException(ExceptionConstant.BUYRECORD_NOT_FOUND_EXCEPTION);
		}
		return buyRecord;
	}

	@Transactional
	public BuyRecord save(BuyRecord buyRecord) {
		buyRecord.setSerialCode(UniqueCodeGenerator.generateSerialCode());
		buyRecord.setTradeNo(UniqueCodeGenerator.generateTradeNo());
		buyRecord.setState(BuyRecordState.POSTED);
		buyRecord.setCreateTime(new Date());
		// 根据委托价格计算持股数
		BigDecimal temp = buyRecord.getApplyAmount().divide(buyRecord.getDelegatePrice(), 2, RoundingMode.HALF_DOWN);
		Integer numberOfStrand = temp.divideAndRemainder(BigDecimal.valueOf(100))[0].multiply(BigDecimal.valueOf(100))
				.intValue();
		buyRecord.setNumberOfStrand(numberOfStrand);
		return buyRecordDao.create(buyRecord);
	}

	public Page<BuyRecord> pagesByQuery(final BuyRecordQuery buyRecordQuery) {
		Pageable pageable = new PageRequest(buyRecordQuery.getPage(), buyRecordQuery.getSize());
		Page<BuyRecord> pages = buyRecordDao.page(new Specification<BuyRecord>() {
			@Override
			public Predicate toPredicate(Root<BuyRecord> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<>();
				if (buyRecordQuery.getStates() != null && buyRecordQuery.getStates().length > 0) {
					predicateList.add(root.get("state").in(buyRecordQuery.getStates()));
				}
				if (buyRecordQuery.getPublisherId() != null && buyRecordQuery.getPublisherId() > 0) {
					predicateList.add(criteriaBuilder.equal(root.get("publisherId").as(Long.class),
							buyRecordQuery.getPublisherId()));
				}
				if (buyRecordQuery.getStartCreateTime() != null) {
					predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Date.class),
							buyRecordQuery.getStartCreateTime()));
				}
				if (buyRecordQuery.getEndCreateTime() != null) {
					predicateList.add(criteriaBuilder.lessThan(root.get("createTime").as(Date.class),
							buyRecordQuery.getStartCreateTime()));
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
		return result;
	}

	@Transactional
	public void remove(Long buyRecordId) {
		buyRecordDao.delete(buyRecordId);
	}

	@Transactional
	public BuyRecord buyLock(Long investorId, Long id, String delegateNumber) {
		BuyRecord buyRecord = findBuyRecord(id);
		if (buyRecord.getState() != BuyRecordState.POSTED) {
			throw new ServiceException(ExceptionConstant.BUYRECORD_STATE_NOTMATCH_OPERATION_NOTSUPPORT_EXCEPTION);
		}
		buyRecord.setInvestorId(investorId);
		buyRecord.setDelegateNumber(delegateNumber);
		// 修改点买记录状态
		return changeState(buyRecord);
	}

	@Transactional
	public BuyRecord buyInto(Long investorId, Long id, BigDecimal buyingPrice) {
		BuyRecord buyRecord = findBuyRecord(id);
		if (buyRecord.getState() != BuyRecordState.BUYLOCK) {
			throw new ServiceException(ExceptionConstant.BUYRECORD_ISNOTLOCK_EXCEPTION);
		}
		logger.info("订单是否相等:{},{}", investorId, buyRecord.getInvestorId());
		if (!investorId.equals(buyRecord.getInvestorId())) {
			throw new ServiceException(ExceptionConstant.BUYRECORD_INVESTORID_NOTMATCH_EXCEPTION);
		}
		buyRecord.setBuyingPrice(buyingPrice);
		buyRecord.setBuyingTime(new Date());
		buyRecord.setNumberOfStrand(buyRecord.getNumberOfStrand());
		// 止盈点位价格 = 买入价格 + ((市值 * 止盈点)/股数)
		buyRecord.setProfitPosition(buyingPrice.add(buyRecord.getApplyAmount().multiply(buyRecord.getProfitPoint())
				.divide(new BigDecimal(buyRecord.getNumberOfStrand()), 2, RoundingMode.HALF_UP)));
		// 止盈预警点位价格 = 买入价格 + ((市值 * (止盈点-0.05))/股数)
		buyRecord.setProfitWarnPosition(buyingPrice
				.add(buyRecord.getApplyAmount().multiply(buyRecord.getProfitPoint().subtract(new BigDecimal(0.05)))
						.divide(new BigDecimal(buyRecord.getNumberOfStrand()), 2, RoundingMode.HALF_UP)));
		// 止损点位价格 = 买入价格 - ((市值 * 止损点)/股数)
		buyRecord.setLossPosition(
				buyingPrice.subtract(buyRecord.getApplyAmount().multiply(buyRecord.getLossPoint().abs())
						.divide(new BigDecimal(buyRecord.getNumberOfStrand()), 2, RoundingMode.HALF_UP)));
		// 止损预警点位价格 = 买入价格 - ((市值 * (止损点+0.05))/股数)
		buyRecord.setLossWarnPosition(buyingPrice.subtract(
				buyRecord.getApplyAmount().multiply(buyRecord.getLossPoint().abs().subtract(new BigDecimal(0.05)))
						.divide(new BigDecimal(buyRecord.getNumberOfStrand()), 2, RoundingMode.HALF_UP)));
		// 修改点买记录状态
		return changeState(buyRecord);
	}

	@Transactional
	public BuyRecord sellLock(Long lockUserId, Long id, WindControlType windControlType) {
		BuyRecord buyRecord = findBuyRecord(id);
		if (buyRecord.getState() != BuyRecordState.HOLDPOSITION) {
			throw new ServiceException(ExceptionConstant.BUYRECORD_STATE_NOTMATCH_OPERATION_NOTSUPPORT_EXCEPTION);
		}
		buyRecord.setWindControlType(windControlType);
		if (lockUserId != null && windControlType != WindControlType.PUBLISHERAPPLY) {
			if (!lockUserId.equals(buyRecord.getInvestorId())) {
				throw new ServiceException(ExceptionConstant.BUYRECORD_INVESTORID_NOTMATCH_EXCEPTION);
			}
		}
		// 修改点买记录状态
		return changeState(buyRecord);
	}

	@Transactional
	public BuyRecord sellOut(Long investorId, Long id, BigDecimal sellingPrice) {
		BuyRecord buyRecord = buyRecordDao.retrieve(id);
		if (buyRecord.getState() != BuyRecordState.SELLLOCK) {
			throw new ServiceException(ExceptionConstant.BUYRECORD_ISNOTLOCK_EXCEPTION);
		}
		if (!investorId.equals(buyRecord.getInvestorId())) {
			throw new ServiceException(ExceptionConstant.BUYRECORD_INVESTORID_NOTMATCH_EXCEPTION);
		}
		buyRecord.setSellingPrice(sellingPrice);
		buyRecord.setSellingTime(new Date());
		BigDecimal profitOrLoss = sellingPrice.subtract(buyRecord.getBuyingPrice())
				.multiply(new BigDecimal(buyRecord.getNumberOfStrand()));
		// 结算
		Settlement settlement = new Settlement();
		settlement.setBuyRecord(buyRecord);
		settlement.setProfitOrLoss(profitOrLoss);
		settlement.setInvestorProfitOrLoss(new BigDecimal(0));
		settlement.setSettlementTime(new Date());
		if (profitOrLoss.compareTo(BigDecimal.ZERO) >= 0) {
			settlement.setPublisherProfitOrLoss(profitOrLoss.multiply(new BigDecimal(0.9)));
			settlement.setInvestorProfitOrLoss(profitOrLoss.multiply(new BigDecimal(1).subtract(new BigDecimal(0.9))));
		} else {
			if (profitOrLoss.abs().compareTo(buyRecord.getReserveFund()) > 0) {
				settlement.setPublisherProfitOrLoss(buyRecord.getReserveFund().multiply(new BigDecimal(-1)));
				settlement.setInvestorProfitOrLoss(profitOrLoss.subtract(settlement.getPublisherProfitOrLoss()));
			} else {
				settlement.setPublisherProfitOrLoss(profitOrLoss);
			}
		}
		settlementDao.create(settlement);
		// 退回保证金
		accountService.returnReserveFund(buyRecord.getPublisherId(), buyRecord.getId(), buyRecord.getSerialCode(),
				settlement.getPublisherProfitOrLoss());
		// 修改点买记录状态
		return changeState(buyRecord);
	}

}
