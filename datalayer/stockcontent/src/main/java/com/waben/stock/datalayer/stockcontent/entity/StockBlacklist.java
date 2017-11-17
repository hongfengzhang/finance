package com.waben.stock.datalayer.stockcontent.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.waben.stock.interfaces.dto.stockcontent.StockBlacklistDto;

import net.sf.cglib.beans.BeanCopier;

/**
 * 股票黑名单
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "stock_blacklist")
public class StockBlacklist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 股票ID
	 */
	private Long stockId;
	/**
	 * 股票名称
	 */
	@Column(name = "name")
	private String name;
	/**
	 * 股票代码
	 */
	@Column(name = "code", unique = true)
	private String code;
	/**
	 * 拼音缩写，股票名称的首字母
	 */
	@Column(name = "pinyin_abbr")
	private String pinyinAbbr;
	/**
	 * 加入黑名单原因
	 */
	private String blacklistReason;
	/**
	 * 加入黑名单时间
	 */
	private String blacklistTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public String getBlacklistReason() {
		return blacklistReason;
	}

	public void setBlacklistReason(String blacklistReason) {
		this.blacklistReason = blacklistReason;
	}

	public String getBlacklistTime() {
		return blacklistTime;
	}

	public void setBlacklistTime(String blacklistTime) {
		this.blacklistTime = blacklistTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPinyinAbbr() {
		return pinyinAbbr;
	}

	public void setPinyinAbbr(String pinyinAbbr) {
		this.pinyinAbbr = pinyinAbbr;
	}

	public StockBlacklistDto copy() {
		StockBlacklistDto result = new StockBlacklistDto();
		BeanCopier copier = BeanCopier.create(StockBlacklist.class, StockBlacklistDto.class, false);
		copier.copy(this, result, null);
		return result;
	}

}
