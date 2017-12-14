package com.waben.stock.interfaces.dto.publisher;

import java.math.BigDecimal;

import com.waben.stock.interfaces.enums.WithdrawalsState;

/**
 * 提现订单
 * 
 * @author luomengan
 *
 */
public class WithdrawalsOrderDto {

	private Long id;
	/**
	 * 提现单号
	 */
	private String withdrawalsNo;
	/**
	 * 第三方代扣单号
	 */
	private String thirdWithdrawalsNo;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 代扣状态
	 */
	private WithdrawalsState state;
	/**
	 * 发布人ID
	 */
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
