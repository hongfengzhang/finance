package com.waben.stock.datalayer.publisher.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.waben.stock.datalayer.publisher.entity.CapitalAccount;
import com.waben.stock.datalayer.publisher.entity.CapitalFlow;
import com.waben.stock.datalayer.publisher.entity.CapitalFlowExtend;
import com.waben.stock.datalayer.publisher.entity.FrozenCapital;
import com.waben.stock.datalayer.publisher.entity.Publisher;
import com.waben.stock.datalayer.publisher.entity.WithdrawalsOrder;
import com.waben.stock.datalayer.publisher.repository.CapitalAccountDao;
import com.waben.stock.datalayer.publisher.repository.CapitalFlowDao;
import com.waben.stock.datalayer.publisher.repository.CapitalFlowExtendDao;
import com.waben.stock.datalayer.publisher.repository.FrozenCapitalDao;
import com.waben.stock.datalayer.publisher.repository.PublisherDao;
import com.waben.stock.datalayer.publisher.repository.WithdrawalsOrderDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.enums.CapitalFlowExtendType;
import com.waben.stock.interfaces.enums.CapitalFlowType;
import com.waben.stock.interfaces.enums.FrozenCapitalStatus;
import com.waben.stock.interfaces.enums.FrozenCapitalType;
import com.waben.stock.interfaces.enums.WithdrawalsState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.query.CapitalAccountQuery;
import com.waben.stock.interfaces.pojo.query.CapitalFlowExtendQuery;

@Service
public class CapitalAccountService {

	@Autowired
	private CapitalAccountDao capitalAccountDao;

	@Autowired
	private CapitalFlowDao flowDao;

	@Autowired
	private CapitalFlowExtendDao flowExtendDao;

	@Autowired
	private FrozenCapitalDao frozenCapitalDao;

	@Autowired
	private PublisherDao publisherDao;

	@Autowired
	private CapitalFlowExtendService extendService;

	@Autowired
	private WithdrawalsOrderDao withdrawalsOrderDao;

	/**
	 * 根据发布人系列号获取资金账户
	 */
	public CapitalAccount findByPublisherSerialCode(String publisherSerialCode) {
		return capitalAccountDao.retriveByPublisherSerialCode(publisherSerialCode);
	}

	/**
	 * 根据发布人ID获取资金账户
	 */
	public CapitalAccount findByPublisherId(Long publisherId) {
		return capitalAccountDao.retriveByPublisherId(publisherId);
	}

	/**
	 * 修改支付密码
	 */
	public void modifyPaymentPassword(Long publisherId, String paymentPassword) {
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		account.setPaymentPassword(paymentPassword);
		capitalAccountDao.update(account);
	}

	/**
	 * 充值
	 */
	@Transactional
	public synchronized CapitalAccount recharge(Long publisherId, BigDecimal amount) {
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		Date date = new Date();
		increaseAmount(account, amount, date);
		flowDao.create(publisherId, account.getPublisherSerialCode(), CapitalFlowType.Recharge, amount.abs(), date);
		return findByPublisherId(publisherId);
	}

	/**
	 * 提现
	 */
	@Transactional
	public synchronized CapitalAccount withdrawals(Long publisherId, String withdrawalsNo,
			WithdrawalsState withdrawalsState) {
		WithdrawalsOrder withdrawalsOrder = withdrawalsOrderDao.retrieveByWithdrawalsNo(withdrawalsNo);
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		if (withdrawalsOrder.getState() == WithdrawalsState.PROCESSING
				&& withdrawalsState != WithdrawalsState.PROCESSING) {
			BigDecimal amount = withdrawalsOrder.getAmount();
			Date date = new Date();
			if (withdrawalsState == WithdrawalsState.PROCESSED) {
				// 修改提现订单的状态
				withdrawalsOrder.setState(withdrawalsState);
				withdrawalsOrder.setUpdateTime(new Date());
				withdrawalsOrderDao.update(withdrawalsOrder);
				// 解冻
				FrozenCapital frozen = frozenCapitalDao.retriveByPublisherIdAndWithdrawalsNo(publisherId,
						withdrawalsNo);
				frozen.setStatus(FrozenCapitalStatus.Thaw);
				frozen.setThawTime(date);
				frozenCapitalDao.update(frozen);
				// 修改账户的总金额、冻结金额
				account.setBalance(account.getBalance().subtract(amount));
				account.setFrozenCapital(account.getFrozenCapital().subtract(amount));
				capitalAccountDao.update(account);
				// 产生资金流水
				flowDao.create(publisherId, account.getPublisherSerialCode(), CapitalFlowType.Withdrawals,
						amount.abs().multiply(new BigDecimal(-1)), date);
				return findByPublisherId(publisherId);
			} else if (withdrawalsState == WithdrawalsState.FAILURE) {
				// 修改提现订单的状态
				withdrawalsOrder.setState(WithdrawalsState.RETREAT);
				withdrawalsOrder.setUpdateTime(new Date());
				withdrawalsOrderDao.update(withdrawalsOrder);
				// 解冻
				FrozenCapital frozen = frozenCapitalDao.retriveByPublisherIdAndWithdrawalsNo(publisherId,
						withdrawalsNo);
				frozen.setStatus(FrozenCapitalStatus.Thaw);
				frozen.setThawTime(date);
				frozenCapitalDao.update(frozen);
				// 修改账户的总金额、冻结金额
				account.setFrozenCapital(account.getFrozenCapital().subtract(amount));
				account.setAvailableBalance(account.getAvailableBalance().add(amount));
				capitalAccountDao.update(account);
			}
		}
		return account;
	}

	/**
	 * 信息服务费和赔付保证金
	 */
	@Transactional
	public synchronized CapitalAccount serviceFeeAndReserveFund(Long publisherId, Long buyRecordId,
			BigDecimal serviceFee, BigDecimal reserveFund) {
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		Date date = new Date();
		reduceAmount(account, serviceFee, reserveFund, date);
		// 保存流水
		CapitalFlow serviceFeeFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
				CapitalFlowType.ServiceFee, serviceFee.abs().multiply(new BigDecimal(-1)), date);
		CapitalFlowExtend serviceFeeExtend = new CapitalFlowExtend(serviceFeeFlow, CapitalFlowExtendType.BUYRECORD,
				buyRecordId);
		flowExtendDao.create(serviceFeeExtend);

		CapitalFlow reserveFundFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
				CapitalFlowType.ReserveFund, reserveFund.abs().multiply(new BigDecimal(-1)), date);
		CapitalFlowExtend reserveFundExtend = new CapitalFlowExtend(reserveFundFlow, CapitalFlowExtendType.BUYRECORD,
				buyRecordId);
		flowExtendDao.create(reserveFundExtend);
		// 保存冻结资金记录
		FrozenCapital frozen = new FrozenCapital();
		frozen.setAmount(reserveFund.abs());
		frozen.setBuyRecordId(buyRecordId);
		frozen.setFrozenTime(date);
		frozen.setPublisherId(publisherId);
		frozen.setStatus(FrozenCapitalStatus.Frozen);
		frozen.setType(FrozenCapitalType.ReserveFund);
		frozenCapitalDao.create(frozen);
		return findByPublisherId(publisherId);
	}

	/**
	 * 递延费
	 */
	@Transactional
	public synchronized CapitalAccount deferredCharges(Long publisherId, Long buyRecordId, BigDecimal amount) {
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		Date date = new Date();
		reduceAmount(account, amount, date);
		CapitalFlow deferredChargesFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
				CapitalFlowType.DeferredCharges, amount.abs().multiply(new BigDecimal(-1)), date);
		CapitalFlowExtend deferredChargesExtend = new CapitalFlowExtend(deferredChargesFlow,
				CapitalFlowExtendType.BUYRECORD, buyRecordId);
		flowExtendDao.create(deferredChargesExtend);
		return findByPublisherId(publisherId);
	}

	/**
	 * 退回赔付保证金和盈亏
	 */
	@Transactional
	public synchronized CapitalAccount returnReserveFund(Long publisherId, Long buyRecordId, String buyRecordSerialCode,
			BigDecimal profitOrLoss) {
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		Date date = new Date();
		// 获取冻结资金记录
		FrozenCapital frozen = frozenCapitalDao.retriveByPublisherIdAndBuyRecordId(publisherId, buyRecordId);
		if (frozen.getStatus() == FrozenCapitalStatus.Thaw) {
			return account;
		}
		BigDecimal frozenAmount = frozen.getAmount();
		if (profitOrLoss.compareTo(new BigDecimal(0)) >= 0) {
			// 退回全部冻结资金
			thawAmount(account, frozenAmount, frozenAmount, date);
			CapitalFlow returnReserveFundFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
					CapitalFlowType.ReturnReserveFund, frozenAmount.abs(), date);
			CapitalFlowExtend returnReserveFundExtend = new CapitalFlowExtend(returnReserveFundFlow,
					CapitalFlowExtendType.BUYRECORD, buyRecordId);
			flowExtendDao.create(returnReserveFundExtend);
			// 盈利
			if (profitOrLoss.compareTo(new BigDecimal(0)) > 0) {
				increaseAmount(account, profitOrLoss, date);
				CapitalFlow profitFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
						CapitalFlowType.Profit, profitOrLoss.abs(), date);
				CapitalFlowExtend profitExtend = new CapitalFlowExtend(profitFlow, CapitalFlowExtendType.BUYRECORD,
						buyRecordId);
				flowExtendDao.create(profitExtend);
			}
		} else {
			// 亏损
			BigDecimal lossAmountAbs = profitOrLoss.abs();
			if (lossAmountAbs.compareTo(frozenAmount) > 0) {
				// 最多亏损保证金
				lossAmountAbs = frozenAmount;
			}
			BigDecimal returnFrozenAmount = frozenAmount.subtract(lossAmountAbs);
			if (returnFrozenAmount.compareTo(new BigDecimal(0)) > 0) {
				// 退回部分保证金
				thawAmount(account, returnFrozenAmount, frozenAmount, date);
				CapitalFlow returnReserveFundFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
						CapitalFlowType.ReturnReserveFund, returnFrozenAmount.abs(), date);
				CapitalFlowExtend returnReserveFundExtend = new CapitalFlowExtend(returnReserveFundFlow,
						CapitalFlowExtendType.BUYRECORD, buyRecordId);
				flowExtendDao.create(returnReserveFundExtend);
			}
			CapitalFlow lossFlow = flowDao.create(publisherId, account.getPublisherSerialCode(), CapitalFlowType.Loss,
					lossAmountAbs.abs().multiply(new BigDecimal(-1)), date);
			CapitalFlowExtend lossExtend = new CapitalFlowExtend(lossFlow, CapitalFlowExtendType.BUYRECORD,
					buyRecordId);
			flowExtendDao.create(lossExtend);
		}
		// 修改冻结记录为解冻状态
		frozen.setStatus(FrozenCapitalStatus.Thaw);
		frozen.setThawTime(new Date());
		frozenCapitalDao.update(frozen);
		// 修改账户的冻结资金数
		account.setFrozenCapital(account.getFrozenCapital().subtract(frozen.getAmount()));
		capitalAccountDao.update(account);
		// 是否为被推广人的第一笔单，如果是，推广人赚取10%的服务费
		Publisher publisher = publisherDao.retrieve(publisherId);
		if (publisher.getPromoter() != null && !"".equals(publisher.getPromoter().trim())) {
			Publisher promoter = publisherDao.retrieveByPromotionCode(publisher.getPromoter().trim());
			if (promoter != null) {
				CapitalFlowExtendQuery query = new CapitalFlowExtendQuery();
				query.setPage(0);
				query.setSize(10);
				query.setType(CapitalFlowType.Promotion);
				query.setPublisherId(promoter.getId());
				query.setExtendType(CapitalFlowExtendType.PUBLISHER);
				query.setExtendId(publisher.getId());
				Page<CapitalFlowExtend> flowPage = extendService.pagesByQuery(query);
				if (flowPage.getContent().size() == 0) {
					// 获取当前点买记录的服务费
					query.setPage(0);
					query.setSize(10);
					query.setType(CapitalFlowType.ServiceFee);
					query.setPublisherId(publisherId);
					query.setExtendType(CapitalFlowExtendType.BUYRECORD);
					query.setExtendId(buyRecordId);
					Page<CapitalFlowExtend> serviceFee = extendService.pagesByQuery(query);
					BigDecimal promotionAmount = serviceFee.getContent().get(0).getFlow().getAmount().abs()
							.multiply(new BigDecimal(0.1));
					CapitalAccount promoterAccount = capitalAccountDao.retriveByPublisherId(promoter.getId());
					increaseAmount(promoterAccount, promotionAmount, date);
					CapitalFlow promotionFlow = flowDao.create(promoter.getId(), promoter.getSerialCode(),
							CapitalFlowType.Promotion, promotionAmount, date);
					CapitalFlowExtend promotionExtend = new CapitalFlowExtend(promotionFlow,
							CapitalFlowExtendType.PUBLISHER, publisherId);
					flowExtendDao.create(promotionExtend);
				}
			}
		}
		return findByPublisherId(publisherId);
	}

	public CapitalAccount revoke(Long publisherId, Long buyRecordId, BigDecimal serviceFee) {
		Date date = new Date();
		// 解冻保证金
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		FrozenCapital frozenCapital = findFrozenCapital(publisherId, buyRecordId);
		frozenCapital.setStatus(FrozenCapitalStatus.Thaw);
		frozenCapital.setThawTime(date);
		thawAmount(account, frozenCapital.getAmount(), frozenCapital.getAmount(), date);
		CapitalFlow returnReserveFundFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
				CapitalFlowType.ReturnReserveFund, frozenCapital.getAmount().abs(), date);
		CapitalFlowExtend returnReserveFundExtend = new CapitalFlowExtend(returnReserveFundFlow,
				CapitalFlowExtendType.BUYRECORD, buyRecordId);
		flowExtendDao.create(returnReserveFundExtend);
		// 退回服务费
		increaseAmount(account, serviceFee, date);
		CapitalFlow revokeServiceFeeFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
				CapitalFlowType.Revoke, serviceFee, date);
		CapitalFlowExtend revokeServiceFeeExtend = new CapitalFlowExtend(revokeServiceFeeFlow,
				CapitalFlowExtendType.BUYRECORD, buyRecordId);
		flowExtendDao.create(revokeServiceFeeExtend);
		account.setFrozenCapital(account.getFrozenCapital().subtract(frozenCapital.getAmount()));
		capitalAccountDao.update(account);
		return findByPublisherId(publisherId);
	}

	public FrozenCapital findFrozenCapital(Long publisherId, Long buyRecordId) {
		return frozenCapitalDao.retriveByPublisherIdAndBuyRecordId(publisherId, buyRecordId);
	}

	/**
	 * 解冻金额
	 * 
	 * @param account
	 *            资金账户
	 * @param amount
	 *            退回金额
	 * @param frozenAmount
	 *            原先冻结资金
	 * 
	 */
	private void thawAmount(CapitalAccount account, BigDecimal amount, BigDecimal frozenAmount, Date date) {
		account.setBalance(account.getBalance().subtract(frozenAmount.subtract(amount)));
		account.setAvailableBalance(account.getAvailableBalance().add(amount));
		account.setUpdateTime(date);
		capitalAccountDao.update(account);
	}

	/**
	 * 账户增加金额
	 * 
	 * @param account
	 *            资金账户
	 * @param amount
	 *            金额
	 */
	private void increaseAmount(CapitalAccount account, BigDecimal amount, Date date) {
		account.setBalance(account.getBalance().add(amount));
		account.setAvailableBalance(account.getAvailableBalance().add(amount));
		account.setUpdateTime(date);
		capitalAccountDao.update(account);
	}

	/**
	 * 账户减少金额
	 * 
	 * @param account
	 *            资金账户
	 * @param amount
	 *            金额
	 */
	private void reduceAmount(CapitalAccount account, BigDecimal amount, Date date) {
		BigDecimal amountAbs = amount.abs();
		// 判断账余额是否足够
		if (account.getAvailableBalance().compareTo(amountAbs) < 0) {
			throw new ServiceException(ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION);
		}
		account.setBalance(account.getBalance().subtract(amount));
		account.setAvailableBalance(account.getAvailableBalance().subtract(amount));
		account.setUpdateTime(date);
		capitalAccountDao.update(account);
	}

	/**
	 * 账户减少金额
	 * 
	 * @param account
	 *            资金账户
	 * @param amount
	 *            金额
	 */
	private void reduceAmount(CapitalAccount account, BigDecimal amount, BigDecimal frozenCapital, Date date) {
		BigDecimal amountAbs = amount.abs();
		BigDecimal frozenCapitalAbs = frozenCapital.abs();
		BigDecimal totalAmountAbs = amountAbs.add(frozenCapitalAbs);
		// 判断账余额是否足够
		if (account.getAvailableBalance().compareTo(totalAmountAbs) < 0) {
			throw new ServiceException(ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION);
		}
		account.setBalance(account.getBalance().subtract(amountAbs));
		account.setAvailableBalance(account.getAvailableBalance().subtract(totalAmountAbs));
		account.setFrozenCapital(account.getFrozenCapital().add(frozenCapital));
		account.setUpdateTime(date);
		capitalAccountDao.update(account);
	}

	public Page<CapitalAccount> pages(final CapitalAccountQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<CapitalAccount> pages = capitalAccountDao.page(new Specification<CapitalAccount>() {
			@Override
			public Predicate toPredicate(Root<CapitalAccount> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				if (!StringUtils.isEmpty(query.getId())) {
					Predicate typeQuery = criteriaBuilder.equal(root.get("id").as(String.class), query.getId());
					criteriaQuery.where(criteriaBuilder.and(typeQuery));
				}
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

}
