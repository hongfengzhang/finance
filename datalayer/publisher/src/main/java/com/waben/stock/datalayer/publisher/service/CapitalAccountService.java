package com.waben.stock.datalayer.publisher.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.publisher.entity.CapitalAccount;
import com.waben.stock.datalayer.publisher.entity.FrozenCapital;
import com.waben.stock.datalayer.publisher.repository.CapitalAccountDao;
import com.waben.stock.datalayer.publisher.repository.CapitalFlowDao;
import com.waben.stock.datalayer.publisher.repository.FrozenCapitalDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.enums.CapitalFlowType;
import com.waben.stock.interfaces.enums.FrozenCapitalStatus;
import com.waben.stock.interfaces.exception.ServiceException;

@Service
public class CapitalAccountService {

	@Autowired
	private CapitalAccountDao capitalAccountDao;

	@Autowired
	private CapitalFlowDao capitalFlowDao;

	@Autowired
	private FrozenCapitalDao frozenCapitalDao;

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
	public CapitalAccount recharge(Long publisherId, BigDecimal amount) {
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		Date date = new Date();
		increaseAmount(account, amount, date);
		capitalFlowDao.create(publisherId, account.getPublisherSerialCode(), CapitalFlowType.Recharge, amount.abs(),
				date);
		return findByPublisherId(publisherId);
	}

	/**
	 * 提现
	 */
	@Transactional
	public CapitalAccount withdrawals(Long publisherId, BigDecimal amount) {
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		Date date = new Date();
		reduceAmount(account, amount, date);
		capitalFlowDao.create(publisherId, account.getPublisherSerialCode(), CapitalFlowType.Recharge,
				amount.abs().multiply(new BigDecimal(-1)), date);
		return findByPublisherId(publisherId);
	}

	/**
	 * 信息服务费和赔付保证金
	 */
	@Transactional
	public CapitalAccount serviceFeeAndReserveFund(Long publisherId, Long buyRecordId, String buyRecordSerialCode,
			BigDecimal serviceFee, BigDecimal reserveFund) {
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		Date date = new Date();
		reduceAmount(account, serviceFee, reserveFund, date);
		capitalFlowDao.create(publisherId, account.getPublisherSerialCode(), CapitalFlowType.ServiceFee,
				serviceFee.abs().multiply(new BigDecimal(-1)), date);
		capitalFlowDao.create(publisherId, account.getPublisherSerialCode(), CapitalFlowType.ReserveFund,
				reserveFund.abs().multiply(new BigDecimal(-1)), date);
		// 保存冻结资金记录
		FrozenCapital frozen = new FrozenCapital();
		frozen.setAmount(reserveFund.abs());
		frozen.setBuyRecordId(buyRecordId);
		frozen.setBuyRecordSerialCode(buyRecordSerialCode);
		frozen.setFrozenTime(date);
		frozen.setPublisherId(publisherId);
		frozen.setPublisherSerialCode(account.getPublisherSerialCode());
		frozen.setStatus(FrozenCapitalStatus.Frozen);
		frozenCapitalDao.create(frozen);
		return findByPublisherId(publisherId);
	}

	/**
	 * 递延费
	 */
	@Transactional
	public CapitalAccount deferredCharges(Long publisherId, BigDecimal amount) {
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		Date date = new Date();
		reduceAmount(account, amount, date);
		capitalFlowDao.create(publisherId, account.getPublisherSerialCode(), CapitalFlowType.DeferredCharges,
				amount.abs().multiply(new BigDecimal(-1)), date);
		return findByPublisherId(publisherId);
	}

	/**
	 * 退回赔付保证金和盈亏
	 */
	@Transactional
	public CapitalAccount returnReserveFund(Long publisherId, Long buyRecordId, String buyRecordSerialCode,
			BigDecimal profitOrLoss) {
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		Date date = new Date();
		// 获取冻结资金记录
		FrozenCapital frozen = frozenCapitalDao.retriveByPublisherIdAndBuyRecordId(publisherId, buyRecordId);
		BigDecimal frozenAmount = frozen.getAmount();
		if (profitOrLoss.compareTo(new BigDecimal(0)) >= 0) {
			// 退回全部冻结资金
			increaseAmount(account, frozenAmount, date);
			capitalFlowDao.create(publisherId, account.getPublisherSerialCode(), CapitalFlowType.ReturnReserveFund,
					frozenAmount.abs(), date);
			// 盈利
			if (profitOrLoss.compareTo(new BigDecimal(0)) > 0) {
				increaseAmount(account, profitOrLoss, date);
				capitalFlowDao.create(publisherId, account.getPublisherSerialCode(), CapitalFlowType.Profit,
						profitOrLoss.abs(), date);
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
				increaseAmount(account, returnFrozenAmount, date);
				capitalFlowDao.create(publisherId, account.getPublisherSerialCode(), CapitalFlowType.ReturnReserveFund,
						returnFrozenAmount.abs(), date);
			}
			capitalFlowDao.create(publisherId, account.getPublisherSerialCode(), CapitalFlowType.Loss,
					lossAmountAbs.abs().multiply(new BigDecimal(-1)), date);
		}
		return findByPublisherId(publisherId);
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

}
