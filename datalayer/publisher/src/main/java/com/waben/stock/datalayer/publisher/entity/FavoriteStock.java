package com.waben.stock.datalayer.publisher.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.waben.stock.interfaces.dto.publisher.FavoriteStockDto;

import net.sf.cglib.beans.BeanCopier;

/**
 * 收藏股票
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "favorite_stock")
public class FavoriteStock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 策略发布人序列号
	 */
	@Column(name = "publisher_serial_code")
	private String publisherSerialCode;
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
	@Column(name = "code")
	private String code;
	/**
	 * 拼音缩写，股票名称的首字母
	 */
	@Column(name = "pinyin_abbr")
	private String pinyinAbbr;
	/**
	 * 收藏时间
	 */
	@Column(name = "favorite_time")
	private Date favoriteTime;
	/**
	 * 排序
	 */
	@Column(name = "sort")
	private Integer sort;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPublisherSerialCode() {
		return publisherSerialCode;
	}

	public void setPublisherSerialCode(String publisherSerialCode) {
		this.publisherSerialCode = publisherSerialCode;
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

	public Date getFavoriteTime() {
		return favoriteTime;
	}

	public void setFavoriteTime(Date favoriteTime) {
		this.favoriteTime = favoriteTime;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public FavoriteStockDto copy() {
		FavoriteStockDto result = new FavoriteStockDto();
		BeanCopier copier = BeanCopier.create(FavoriteStock.class, FavoriteStockDto.class, false);
		copier.copy(this, result, null);
		return result;
	}

}
