package com.waben.stock.futuresgateway.exception;

public enum ExceptionEnum {

	Unknow("1000", "未知异常"),

	Symbol_NotSuported("1001", "不支持的合约类型!"),

	Action_NotSuported("1002", "不支持的交易方向!"),

	UserOrderType_NotSuported("1001", "不支持的合约类型!");

	private ExceptionEnum(String code, String errorMsg) {
		this.code = code;
		this.errorMsg = errorMsg;
	}

	private String code;

	private String errorMsg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
