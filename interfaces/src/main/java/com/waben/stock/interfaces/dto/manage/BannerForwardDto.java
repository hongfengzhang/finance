package com.waben.stock.interfaces.dto.manage;

/**
 * 轮播图跳转 Dto
 * 
 * @author luomengan
 *
 */
public class BannerForwardDto {

	private Long id;
	/**
	 * 跳转代码
	 */
	private String forward;
	/**
	 * 轮播图跳转描述
	 */
	private String described;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getDescribed() {
		return described;
	}

	public void setDescribed(String described) {
		this.described = described;
	}

}
