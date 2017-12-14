package com.waben.stock.datalayer.publisher.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.waben.stock.datalayer.publisher.entity.enumconverter.PaymentStateConverter;
import com.waben.stock.interfaces.enums.WithdrawalsState;

/**
 * 提现订单
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "withdrawals_order")
public class WithdrawalsOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 提现单号
	 */
	@Column(name = "withdrawals_no")
	private String withdrawalsNo;
	/**
	 * 第三方代扣单号
	 */
	@Column(name = "third_withdrawals_no")
	private String thirdWithdrawalsNo;
	/**
	 * 金额
	 */
	@Column(name = "amount")
	private BigDecimal amount;
	/**
	 * 代扣状态
	 */
	@Column(name = "state")
	@Convert(converter = PaymentStateConverter.class)
	private WithdrawalsState state;
	/**
	 * 发布人ID
	 */
	@Column(name = "publisher_id")
	private Long publisherId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWithdrawalsNo() {
		return withdrawalsNo;
	}

	public void setWithdrawalsNo(String withdrawalsNo) {
		this.withdrawalsNo = withdrawalsNo;
	}

	public String getThirdWithdrawalsNo() {
		return thirdWithdrawalsNo;
	}

	public void setThirdWithdrawalsNo(String thirdWithdrawalsNo) {
		this.thirdWithdrawalsNo = thirdWithdrawalsNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public WithdrawalsState getState() {
		return state;
	}

	public void setState(WithdrawalsState state) {
		this.state = state;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

}
