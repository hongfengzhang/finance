package com.waben.stock.datalayer.futures.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 全局设置
 * @author pzl
 *
 */
@Entity
@Table(name = "f_futures_global_Config")
public class FuturesGlobalConfig {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 风控参数
	 */
	private String windControlParameters;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWindControlParameters() {
		return windControlParameters;
	}

	public void setWindControlParameters(String windControlParameters) {
		this.windControlParameters = windControlParameters;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
