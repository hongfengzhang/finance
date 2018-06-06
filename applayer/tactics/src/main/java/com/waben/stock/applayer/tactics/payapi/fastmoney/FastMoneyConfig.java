package com.waben.stock.applayer.tactics.payapi.fastmoney;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FastMoneyConfig {

	@Value("${pay.merchant}")
	private String merchantNo;
	@Value("${pay.key}")
	private String key;
	@Value("${pay.notifyUrl}")
	private String notifyUrl;
	@Value("${pay.frontUrl}")
	private String frontUrl;
	@Value("${pay.h5ProxyfrontUrl}")
	private String h5ProxyfrontUrl;
	@Value("${pay.h5FrontUrl}")
	private String h5FrontUrl;
	@Value("${pay.protocolcallback}")
	private String protocol_callback;

	@Value("${pay.url}")
	private String payUrl;
	@Value("${withdraw.merchant}")
	private String withdrawMerchantNo;
	@Value("${withdraw.key}")
	private String withdrawKey;

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getFrontUrl() {
		return frontUrl;
	}

	public void setFrontUrl(String frontUrl) {
		this.frontUrl = frontUrl;
	}

	public String getH5ProxyfrontUrl() {
		return h5ProxyfrontUrl;
	}

	public void setH5ProxyfrontUrl(String h5ProxyfrontUrl) {
		this.h5ProxyfrontUrl = h5ProxyfrontUrl;
	}

	public String getH5FrontUrl() {
		return h5FrontUrl;
	}

	public void setH5FrontUrl(String h5FrontUrl) {
		this.h5FrontUrl = h5FrontUrl;
	}

	public String getProtocol_callback() {
		return protocol_callback;
	}

	public void setProtocol_callback(String protocol_callback) {
		this.protocol_callback = protocol_callback;
	}

	public String getWithdrawMerchantNo() {
		return withdrawMerchantNo;
	}

	public void setWithdrawMerchantNo(String withdrawMerchantNo) {
		this.withdrawMerchantNo = withdrawMerchantNo;
	}

	public String getWithdrawKey() {
		return withdrawKey;
	}

	public void setWithdrawKey(String withdrawKey) {
		this.withdrawKey = withdrawKey;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

}
