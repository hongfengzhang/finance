package com.waben.stock.datalayer.futures.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 期货订单过夜记录
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "f_futures_overnight_record")
public class FuturesOvernightRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 发布人ID
	 */
	private Long publisherId;
	/**
	 * 对应的订单
	 */
	@ManyToOne
	@JoinColumn(name = "order_id")
	private FuturesOrder order;
	/**
	 * 隔夜保证金
	 */
	private BigDecimal overnightReserveFund;
	/**
	 * 隔夜递延费
	 */
	private BigDecimal overnightDeferredFee;
	/**
	 * 递延日期
	 */
	private Date deferredTime;
	/**
	 * 扣费日期
	 */
	private Date reduceTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public FuturesOrder getOrder() {
		return order;
	}

	public void setOrder(FuturesOrder order) {
		this.order = order;
	}

	public BigDecimal getOvernightReserveFund() {
		return overnightReserveFund;
	}

	public void setOvernightReserveFund(BigDecimal overnightReserveFund) {
		this.overnightReserveFund = overnightReserveFund;
	}

	public BigDecimal getOvernightDeferredFee() {
		return overnightDeferredFee;
	}

	public void setOvernightDeferredFee(BigDecimal overnightDeferredFee) {
		this.overnightDeferredFee = overnightDeferredFee;
	}

	public Date getDeferredTime() {
		return deferredTime;
	}

	public void setDeferredTime(Date deferredTime) {
		this.deferredTime = deferredTime;
	}

	public Date getReduceTime() {
		return reduceTime;
	}

	public void setReduceTime(Date reduceTime) {
		this.reduceTime = reduceTime;
	}

}
