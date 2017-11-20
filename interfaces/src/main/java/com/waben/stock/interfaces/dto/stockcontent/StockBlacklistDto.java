package com.waben.stock.interfaces.dto.stockcontent;

/**
 * 股票黑名单
 * 
 * @author luomengan
 *
 */
public class StockBlacklistDto {

	private Long id;
	/**
	 * 股票ID
	 */
	private Long stockId;
	/**
	 * 股票名称
	 */
	private String name;
	/**
	 * 股票代码
	 */
	private String code;
	/**
	 * 拼音缩写，股票名称的首字母
	 */
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

}
