package com.waben.stock.datalayer.publisher.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 收藏的股票
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
	@Column(name = "code", unique = true)
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

}
