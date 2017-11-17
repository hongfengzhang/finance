package com.waben.stock.datalayer.stockcontent.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.waben.stock.interfaces.dto.stockcontent.StockRecommendDto;

import net.sf.cglib.beans.BeanCopier;

/**
 * 股票推荐
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "stock_recommend")
public class StockRecommend {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 股票ID
	 */
	@Column(name = "stock_id")
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
	 * 排序
	 */
	@Column(name = "sort")
	private Integer sort;
	/**
	 * 推荐时间
	 */
	@Column(name = "recommend_time")
	private Date recommendTime;

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

	public Date getRecommendTime() {
		return recommendTime;
	}

	public void setRecommendTime(Date recommendTime) {
		this.recommendTime = recommendTime;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public StockRecommendDto copy() {
		StockRecommendDto result = new StockRecommendDto();
		BeanCopier copier = BeanCopier.create(StockRecommend.class, StockRecommendDto.class, false);
		copier.copy(this, result, null);
		return result;
	}

}
