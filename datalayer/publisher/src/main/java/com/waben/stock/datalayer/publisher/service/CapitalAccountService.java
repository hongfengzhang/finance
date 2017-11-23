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
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.enums.CapitalFlowType;
import com.waben.stock.interfaces.enums.FrozenCapitalStatus;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.util.CopyBeanUtils;

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
	public CapitalAccountDto findByPublisherSerialCode(String publisherSerialCode) {
		CapitalAccount account = capitalAccountDao.findByPublisherSerialCode(publisherSerialCode);
		return CopyBeanUtils.copyBeanProperties(CapitalAccountDto.class, account, false);
	}

	/**
	 * 根据发布人ID获取资金账户
	 */
	public CapitalAccountDto findByPublisherId(Long publisherId) {
		CapitalAccount account = capitalAccountDao.findByPublisherId(publisherId);
		return CopyBeanUtils.copyBeanProperties(CapitalAccountDto.class, account, false);
	}

	/**
	 * 充值
	 */
	@Transactional
	public CapitalAccountDto recharge(Long publisherId, String publisherSerialCode, BigDecimal amount) {
		CapitalAccount account = capitalAccountDao.findByPublisherId(publisherId);
		Date date = new Date();
		increaseAmount(account, amount, date);
		capitalFlowDao.addCapitalFlow(publisherId, publisherSerialCode, CapitalFlowType.Recharge, amount.abs(), date);
		return findByPublisherId(publisherId);
	}

	/**
	 * 提现
	 */
	@Transactional
	public CapitalAccountDto withdrawals(Long publisherId, String publisherSerialCode, BigDecimal amount) {
		CapitalAccount account = capitalAccountDao.findByPublisherId(publisherId);
		Date date = new Date();
		reduceAmount(account, amount, date);
		capitalFlowDao.addCapitalFlow(publisherId, publisherSerialCode, CapitalFlowType.Recharge,
				amount.abs().multiply(new BigDecimal(-1)), date);
		return findByPublisherId(publisherId);
	}

	/**
	 * 信息服务费和赔付保证金
	 */
	@Transactional
	public CapitalAccountDto serviceFeeAndCompensateMoney(Long publisherId, String publisherSerialCode,
			Long buyRecordId, String buyRecordSerialCode, BigDecimal serviceFeeAmount,
			BigDecimal compensateMoneyAmount) {
		CapitalAccount account = capitalAccountDao.findByPublisherId(publisherId);
		Date date = new Date();
		reduceAmount(account, serviceFeeAmount, compensateMoneyAmount, date);
		capitalFlowDao.addCapitalFlow(publisherId, publisherSerialCode, CapitalFlowType.ServiceFee,
				serviceFeeAmount.abs().multiply(new BigDecimal(-1)), date);
		capitalFlowDao.addCapitalFlow(publisherId, publisherSerialCode, CapitalFlowType.CompensateMoney,
				compensateMoneyAmount.abs().multiply(new BigDecimal(-1)), date);
		// 保存冻结资金记录
		FrozenCapital frozen = new FrozenCapital();
		frozen.setAmount(compensateMoneyAmount.abs());
		frozen.setBuyRecordId(buyRecordId);
		frozen.setBuyRecordSerialCode(buyRecordSerialCode);
		frozen.setFrozenTime(date);
		frozen.setPublisherId(publisherId);
		frozen.setPublisherSerialCode(publisherSerialCode);
		frozen.setStatus(FrozenCapitalStatus.Frozen);
		frozenCapitalDao.create(frozen);
		return findByPublisherId(publisherId);
	}

	/**
	 * 递延费
	 */
	@Transactional
	public CapitalAccountDto deferredCharges(Long publisherId, String publisherSerialCode, BigDecimal amount) {
		CapitalAccount account = capitalAccountDao.findByPublisherId(publisherId);
		Date date = new Date();
		reduceAmount(account, amount, date);
		capitalFlowDao.addCapitalFlow(publisherId, publisherSerialCode, CapitalFlowType.DeferredCharges,
				amount.abs().multiply(new BigDecimal(-1)), date);
		return findByPublisherId(publisherId);
	}

	/**
	 * 退回赔付保证金和盈亏
	 */
	@Transactional
	public CapitalAccountDto returnCompensateAndLoss(Long publisherId, String publisherSerialCode, Long buyRecordId,
			String buyRecordSerialCode, BigDecimal profitOrLoss) {
		CapitalAccount account = capitalAccountDao.findByPublisherId(publisherId);
		Date date = new Date();
		// 获取冻结资金记录
		FrozenCapital frozen = frozenCapitalDao.findByPublisherIdAndBuyRecordId(publisherId, buyRecordId);
		BigDecimal frozenAmount = frozen.getAmount();
		if (profitOrLoss.compareTo(new BigDecimal(0)) >= 0) { // 盈利
			// 退回全部冻结资金
			increaseAmount(account, frozenAmount, date);
			capitalFlowDao.addCapitalFlow(publisherId, publisherSerialCode, CapitalFlowType.ReturnCompensate,
					frozenAmount.abs(), date);
			// 盈利
			if (profitOrLoss.compareTo(new BigDecimal(0)) > 0) {
				increaseAmount(account, profitOrLoss, date);
				capitalFlowDao.addCapitalFlow(publisherId, publisherSerialCode, CapitalFlowType.Profit,
						profitOrLoss.abs(), date);
			}
		} else { // 亏损
			BigDecimal lossAmountAbs = profitOrLoss.abs();
			if (lossAmountAbs.compareTo(frozenAmount) > 0) {
				// 最多亏损保证金
				lossAmountAbs = frozenAmount;
			}
			BigDecimal returnFrozenAmount = frozenAmount.subtract(lossAmountAbs);
			if (returnFrozenAmount.compareTo(new BigDecimal(0)) > 0) {
				// 退回部分保证金
				increaseAmount(account, returnFrozenAmount, date);
				capitalFlowDao.addCapitalFlow(publisherId, publisherSerialCode, CapitalFlowType.ReturnCompensate,
						returnFrozenAmount.abs(), date);
			}
			capitalFlowDao.addCapitalFlow(publisherId, publisherSerialCode, CapitalFlowType.Loss,
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
