package com.waben.stock.interfaces.exception;

public class ServiceException extends RuntimeException {

	private String type;
	private String message;
	public ServiceException(String type) {
		super(type);
		this.type = type;
	}

	public ServiceException(String type, Throwable cause) {
		super(type, cause);
		this.type = type;
	}

	//提示错误信息
	public String message() {
		if (ExceptionMap.exceptionMap.containsKey(type)) {
			message = ExceptionMap.exceptionMap.get(type);
		}
		return message;
	}

	public String getType() {
		return type;
	}
}
