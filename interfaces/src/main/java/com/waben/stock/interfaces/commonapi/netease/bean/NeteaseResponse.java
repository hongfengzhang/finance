package com.waben.stock.interfaces.commonapi.netease.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NeteaseResponse<T> {

	private String msg;

	private int code;

	private T ret;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getRet() {
		return ret;
	}

	public void setRet(T ret) {
		this.ret = ret;
	}

}
