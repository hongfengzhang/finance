package com.waben.stock.datalayer.publisher.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.waben.stock.datalayer.publisher.business.OutsideMessageBusiness;
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
import com.waben.stock.interfaces.enums.OutsideMessageType;
import com.waben.stock.interfaces.enums.ResourceType;
import com.waben.stock.interfaces.enums.WithdrawalsState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.message.OutsideMessage;
import com.waben.stock.interfaces.pojo.query.CapitalAccountQuery;
import com.waben.stock.interfaces.pojo.query.CapitalFlowExtendQuery;
import com.waben.stock.interfaces.util.PasswordCrypt;

@Service
public class CapitalAccountService {

	Logger logger = LoggerFactory.getLogger(getClass());

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

	@Autowired
	private OutsideMessageBusiness outsideMessageBusiness;

	/**
	 * 根据发布人系列号获取资金账户
	 */
	public CapitalAccount findByPublisherSerialCode(String publisherSerialCode) {
		return capitalAccountDao.retriveByPublisherSerialCode(publisherSerialCode);
	}

	public CapitalAccount findById(Long id) {
		return capitalAccountDao.retrieve(id);
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
		account.setPaymentPassword(PasswordCrypt.crypt(paymentPassword));
		capitalAccountDao.update(account);
	}

	private void sendRechargeOutsideMessage(Long publisherId, BigDecimal amount) {
		try {
			OutsideMessage message = new OutsideMessage();
			message.setPublisherId(publisherId);
			message.setTitle("资金通知");
			message.setContent(String.format("您的资金账户已充值成功+%s元", amount.toString()));
			Map<String, String> extras = new HashMap<>();
			extras.put("title", message.getTitle());
			extras.put("content", String.format("您的资金账户已充值成功<span id=\"money\">+%s元</span>", amount.toString()));
			extras.put("publisherId", String.valueOf(publisherId));
			extras.put("resourceType", ResourceType.PUBLISHER.getIndex());
			extras.put("resourceId", String.valueOf(publisherId));
			extras.put("type", OutsideMessageType.ACCOUNT_RECHARGESUCCESS.getIndex());
			message.setExtras(extras);
			outsideMessageBusiness.send(message);
		} catch (Exception ex) {
			logger.error("发送资金通知失败，{}充值成功{}_{}", publisherId, amount.toString(), ex.getMessage());
		}
	}

	private void sendWithdrawalsOutsideMessage(Long publisherId, BigDecimal amount, boolean isSuccessful) {
		try {
			OutsideMessage message = new OutsideMessage();
			message.setPublisherId(publisherId);
			message.setTitle("资金通知");
			if (isSuccessful) {
				message.setContent(String.format("您的资金账户已成功提现-%s元", amount.toString()));
			} else {
				message.setContent(String.format("您的资金账户提现失败%s元", amount.toString()));
			}
			Map<String, String> extras = new HashMap<>();
			extras.put("title", message.getTitle());
			if (isSuccessful) {
				extras.put("content", String.format("您的资金账户已成功提现<span id=\"money\">-%s元</span>", amount.toString()));
			} else {
				extras.put("content", String.format("您的资金账户提现失败<span id=\"money\">%s元</span>", amount.toString()));
			}
			extras.put("publisherId", String.valueOf(publisherId));
			extras.put("resourceType", ResourceType.PUBLISHER.getIndex());
			extras.put("resourceId", String.valueOf(publisherId));
			if (isSuccessful) {
				extras.put("type", OutsideMessageType.ACCOUNT_WITHDRAWALSSUCCESS.getIndex());
			} else {
				extras.put("type", OutsideMessageType.ACCOUNT_WITHDRAWALFAILED.getIndex());
			}
			message.setExtras(extras);
			outsideMessageBusiness.send(message);
		} catch (Exception ex) {
			logger.error("发送资金通知失败，{}充值成功{}_{}", publisherId, amount.toString(), ex.getMessage());
		}
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

		sendRechargeOutsideMessage(publisherId, amount);
		return findByPublisherId(publisherId);
	}

	/**
	 * 提现改变资金
	 */
	@Transactional
	public synchronized CapitalAccount csa(Long publisherId, BigDecimal amount) {
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		Date date = new Date();
		reduceAmount(account, amount, date);
		flowDao.create(publisherId, account.getPublisherSerialCode(), CapitalFlowType.Withdrawals, amount.abs().multiply(new BigDecimal(-1)), date);
		sendWithdrawalsOutsideMessage(publisherId, amount,true);
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
				sendWithdrawalsOutsideMessage(publisherId, amount.abs(), true);
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
				sendWithdrawalsOutsideMessage(publisherId, amount.abs(), false);
				return findByPublisherId(publisherId);
			}
		}
		return account;
	}

	/**
	 * 信息服务费和赔付保证金
	 */
	@Transactional
	public synchronized CapitalAccount serviceFeeAndReserveFund(Long publisherId, Long buyRecordId,
			BigDecimal serviceFee, BigDecimal reserveFund, BigDecimal deferredFee) {
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		Date date = new Date();
		reduceAmount(account, serviceFee, reserveFund, date);
		if (deferredFee != null && deferredFee.compareTo(new BigDecimal(0)) > 0) {
			reduceAmount(account, deferredFee, date);
		}
		// 保存流水
		if (serviceFee != null && serviceFee.compareTo(new BigDecimal(0)) > 0) {
			CapitalFlow serviceFeeFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
					CapitalFlowType.ServiceFee, serviceFee.abs().multiply(new BigDecimal(-1)), date);
			CapitalFlowExtend serviceFeeExtend = new CapitalFlowExtend(serviceFeeFlow, CapitalFlowExtendType.BUYRECORD,
					buyRecordId);
			flowExtendDao.create(serviceFeeExtend);
		}
		CapitalFlow reserveFundFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
				CapitalFlowType.ReserveFund, reserveFund.abs().multiply(new BigDecimal(-1)), date);
		CapitalFlowExtend reserveFundExtend = new CapitalFlowExtend(reserveFundFlow, CapitalFlowExtendType.BUYRECORD,
				buyRecordId);
		flowExtendDao.create(reserveFundExtend);

		if (deferredFee != null && deferredFee.compareTo(new BigDecimal(0)) > 0) {
			CapitalFlow deferredFeeFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
					CapitalFlowType.DeferredCharges, deferredFee.abs().multiply(new BigDecimal(-1)), date);
			CapitalFlowExtend deferredFeeExtend = new CapitalFlowExtend(deferredFeeFlow,
					CapitalFlowExtendType.BUYRECORD, buyRecordId);
			flowExtendDao.create(deferredFeeExtend);
		}
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
		// 退回全部冻结资金
		thawAmount(account, frozenAmount, frozenAmount, date);
		account.setFrozenCapital(account.getFrozenCapital().subtract(frozenAmount.abs()));
		capitalAccountDao.update(account);
		CapitalFlow returnReserveFundFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
				CapitalFlowType.ReturnReserveFund, frozenAmount.abs(), date);
		CapitalFlowExtend returnReserveFundExtend = new CapitalFlowExtend(returnReserveFundFlow,
				CapitalFlowExtendType.BUYRECORD, buyRecordId);
		flowExtendDao.create(returnReserveFundExtend);
		// 盈亏
		if (profitOrLoss.compareTo(new BigDecimal(0)) > 0) {
			// 盈利
			increaseAmount(account, profitOrLoss, date);
			CapitalFlow profitFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
					CapitalFlowType.Profit, profitOrLoss.abs(), date);
			CapitalFlowExtend profitExtend = new CapitalFlowExtend(profitFlow, CapitalFlowExtendType.BUYRECORD,
					buyRecordId);
			flowExtendDao.create(profitExtend);
		} else if (profitOrLoss.compareTo(new BigDecimal(0)) < 0) {
			// 亏损
			BigDecimal lossAmountAbs = profitOrLoss.abs();
			if (lossAmountAbs.compareTo(frozenAmount) > 0) {
				// 最多亏损保证金
				lossAmountAbs = frozenAmount;
			}
			reduceAmount(account, lossAmountAbs, date);
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
					Page<CapitalFlowExtend> serviceFeePage = extendService.pagesByQuery(query);
					if (serviceFeePage.getContent() != null && serviceFeePage.getContent().size() > 0) {
						BigDecimal serviceFee = serviceFeePage.getContent().get(0).getFlow().getAmount().abs();
						if (serviceFee.compareTo(new BigDecimal(0)) > 0) {
							BigDecimal promotionAmount = serviceFee.multiply(new BigDecimal(0.1));
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
			}
		}
		return findByPublisherId(publisherId);
	}

	@Transactional
	public synchronized CapitalAccount returnDeferredFee(Long publisherId, Long buyRecordId, BigDecimal deferredFee) {
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		Date date = new Date();
		increaseAmount(account, deferredFee, date);
		// 保存流水
		CapitalFlow serviceFeeFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
				CapitalFlowType.ReturnDeferredCharges, deferredFee.abs(), date);
		CapitalFlowExtend serviceFeeExtend = new CapitalFlowExtend(serviceFeeFlow, CapitalFlowExtendType.BUYRECORD,
				buyRecordId);
		flowExtendDao.create(serviceFeeExtend);
		return findByPublisherId(publisherId);
	}

	@Transactional
	public synchronized CapitalAccount revoke(Long publisherId, Long buyRecordId, BigDecimal serviceFee,
			BigDecimal deferredFee) {
		Date date = new Date();
		// 解冻保证金
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		FrozenCapital frozenCapital = findFrozenCapital(publisherId, buyRecordId);
		frozenCapital.setStatus(FrozenCapitalStatus.Thaw);
		frozenCapital.setThawTime(date);
		thawAmount(account, frozenCapital.getAmount(), frozenCapital.getAmount(), date);
		account.setFrozenCapital(account.getFrozenCapital().subtract(frozenCapital.getAmount()));
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
		// 退回递延费
		if (deferredFee != null && deferredFee.compareTo(new BigDecimal(0)) > 0) {
			increaseAmount(account, deferredFee, date);
			CapitalFlow deferredFeeFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
					CapitalFlowType.ReturnDeferredCharges, deferredFee.abs(), date);
			CapitalFlowExtend deferredFeeExtend = new CapitalFlowExtend(deferredFeeFlow,
					CapitalFlowExtendType.BUYRECORD, buyRecordId);
			flowExtendDao.create(deferredFeeExtend);
		}
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
	private synchronized void thawAmount(CapitalAccount account, BigDecimal amount, BigDecimal frozenAmount,
			Date date) {
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
	private synchronized void increaseAmount(CapitalAccount account, BigDecimal amount, Date date) {
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
	private synchronized void reduceAmount(CapitalAccount account, BigDecimal amount, Date date) {
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
	private synchronized void reduceAmount(CapitalAccount account, BigDecimal amount, BigDecimal frozenCapital,
			Date date) {
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
				List<Predicate> predicatesList = new ArrayList<Predicate>();
				if (query.getPublisherId() != null && query.getPublisherId() > 0) {
					Predicate publisherIdQuery = criteriaBuilder.equal(root.get("publisherId").as(Long.class),
							query.getPublisherId());
					predicatesList.add(criteriaBuilder.and(publisherIdQuery));
				}
				if (query.getBeginTime() != null) {
					Predicate updateTimeQuery = criteriaBuilder.between(root.<Date>get("updateTime").as(Date.class),
							query.getBeginTime(), query.getEndTime());
					predicatesList.add(criteriaBuilder.and(updateTimeQuery));
				}
				criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
				criteriaQuery.orderBy(criteriaBuilder.desc(root.<Date>get("updateTime").as(Date.class)));
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

	public CapitalAccount revision(CapitalAccount capitalAccount) {
		CapitalAccount response = capitalAccountDao.update(capitalAccount);
		return response;
	}

	public CapitalAccount rightMoney(Long publisherId, Long optionTradeId, BigDecimal rightMoney) {
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		Date date = new Date();
		reduceAmount(account, rightMoney, date);
		// 保存流水
		CapitalFlow rightMoneyFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
				CapitalFlowType.RightMoney, rightMoney.abs().multiply(new BigDecimal(-1)), date);
		CapitalFlowExtend rightMoneyExtend = new CapitalFlowExtend(rightMoneyFlow,
				CapitalFlowExtendType.STOCKOPTIONTRADE, optionTradeId);
		flowExtendDao.create(rightMoneyExtend);
		return findByPublisherId(publisherId);
	}

	public CapitalAccount returnRightMoney(Long publisherId, Long optionTradeId, BigDecimal rightMoney) {
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		Date date = new Date();
		increaseAmount(account, rightMoney, date);
		// 保存流水
		CapitalFlow returnRightMoneyFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
				CapitalFlowType.ReturnRightMoney, rightMoney.abs(), date);
		CapitalFlowExtend returnRightMoneyExtend = new CapitalFlowExtend(returnRightMoneyFlow,
				CapitalFlowExtendType.STOCKOPTIONTRADE, optionTradeId);
		flowExtendDao.create(returnRightMoneyExtend);
		return findByPublisherId(publisherId);
	}

	public CapitalAccount optionProfit(Long publisherId, Long optionTradeId, BigDecimal profit) {
		CapitalAccount account = capitalAccountDao.retriveByPublisherId(publisherId);
		Date date = new Date();
		increaseAmount(account, profit, date);
		// 保存流水
		CapitalFlow optionProfitFlow = flowDao.create(publisherId, account.getPublisherSerialCode(),
				CapitalFlowType.StockOptionProfit, profit.abs(), date);
		CapitalFlowExtend optionProfitExtend = new CapitalFlowExtend(optionProfitFlow,
				CapitalFlowExtendType.STOCKOPTIONTRADE, optionTradeId);
		flowExtendDao.create(optionProfitExtend);
		return findByPublisherId(publisherId);
	}

}
