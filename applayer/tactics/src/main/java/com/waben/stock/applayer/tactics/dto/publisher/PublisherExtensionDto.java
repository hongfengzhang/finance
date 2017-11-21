package com.waben.stock.applayer.tactics.dto.publisher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PublisherExtensionDto {

	/**
	 * 推广用户总数
	 */
	private int extensionUserCount;
	/**
	 * 推广总收益
	 */
	private BigDecimal extensionTotalProfit;
	/**
	 * 推广链接
	 */
	private String extensionLink;
	/**
	 * 推广用户列表
	 */
	private List<PublisherExtensionUserDto> extensionUserList;

	public int getExtensionUserCount() {
		return extensionUserCount;
	}

	public void setExtensionUserCount(int extensionUserCount) {
		this.extensionUserCount = extensionUserCount;
	}

	public BigDecimal getExtensionTotalProfit() {
		return extensionTotalProfit;
	}

	public void setExtensionTotalProfit(BigDecimal extensionTotalProfit) {
		this.extensionTotalProfit = extensionTotalProfit;
	}

	public String getExtensionLink() {
		return extensionLink;
	}

	public void setExtensionLink(String extensionLink) {
		this.extensionLink = extensionLink;
	}

	public List<PublisherExtensionUserDto> getExtensionUserList() {
		return extensionUserList;
	}

	public void setExtensionUserList(List<PublisherExtensionUserDto> extensionUserList) {
		this.extensionUserList = extensionUserList;
	}

	/**
	 * 推广用户详情
	 * 
	 * @author luomengan
	 *
	 */
	public static class PublisherExtensionUserDto {
		/**
		 * 手机号
		 */
		private String phone;
		/**
		 * 推广收益
		 */
		private BigDecimal extensionProfit;
		/**
		 * 注册时间
		 */
		private Date createTime;

		public PublisherExtensionUserDto(String phone, BigDecimal extensionProfit, Date createTime) {
			this.phone = phone;
			this.extensionProfit = extensionProfit;
			this.createTime = createTime;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public BigDecimal getExtensionProfit() {
			return extensionProfit;
		}

		public void setExtensionProfit(BigDecimal extensionProfit) {
			this.extensionProfit = extensionProfit;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

	}

}
