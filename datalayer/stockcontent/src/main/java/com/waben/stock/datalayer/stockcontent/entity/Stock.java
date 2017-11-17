package com.waben.stock.datalayer.stockcontent.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.waben.stock.interfaces.dto.stockcontent.StockDto;

import net.sf.cglib.beans.BeanCopier;

/**
 * 股票
 * 
 * @author luomengan
 *
 */
@Entity
@Table(name = "stock")
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
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
	 * 所属行业板块，如医药制造、保险、食品饮料、医疗行业等
	 */
	@Column(name = "industry_sector")
	private String industrySector;
	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getIndustrySector() {
		return industrySector;
	}

	public void setIndustrySector(String industrySector) {
		this.industrySector = industrySector;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public StockDto copy() {
		StockDto result = new StockDto();
		BeanCopier copier = BeanCopier.create(Stock.class, StockDto.class, false);
		copier.copy(this, result, null);
		return result;
	}

}
