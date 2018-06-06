package com.waben.stock.applayer.tactics.payapi.fastmoney.bean;

public class PayRequestBean {

	/**
	 * 表单提交的地址
	 */
	private String formAction;
	/**
	 * 字符集
	 * <ul>
	 * <li>1 UTF-8</li>
	 * <li>2 GBK</li>
	 * <li>3 GB2312</li>
	 * </ul>
	 */
	private String inputCharset = "1";
	/**
	 * 前端页面地址
	 */
	private String pageUrl;
	/**
	 * 回台回调地址
	 */
	private String bgUrl;
	/**
	 * 版本
	 */
	private String version = "v2.0";
	/**
	 * 支付页面语种
	 */
	private String language = "1";
	/**
	 * 签名类型
	 * <ul>
	 * <li>4 RSA</li>
	 * </ul>
	 */
	private String signType = "4";
	/**
	 * 人民币账号
	 */
	private String merchantAcctId;
	/**
	 * 商户订单号
	 */
	private String orderId;
	/**
	 * 商户订单金额
	 * <p>
	 * 单位分
	 * </p>
	 */
	private String orderAmount;
	/**
	 * 商户订单提交时间
	 */
	private String orderTime;
	/**
	 * 支付方式
	 */
	private String payType = "00";
	/**
	 * 签名字符串
	 */
	private String signMsg;

	public String getFormAction() {
		return formAction;
	}

	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getBgUrl() {
		return bgUrl;
	}

	public void setBgUrl(String bgUrl) {
		this.bgUrl = bgUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getMerchantAcctId() {
		return merchantAcctId;
	}

	public void setMerchantAcctId(String merchantAcctId) {
		this.merchantAcctId = merchantAcctId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

}
