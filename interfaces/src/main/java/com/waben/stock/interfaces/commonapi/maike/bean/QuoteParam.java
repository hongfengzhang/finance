package com.waben.stock.interfaces.commonapi.maike.bean;

import com.waben.stock.interfaces.commonapi.maike.util.Md5Util;

public class QuoteParam {

	private String ApiId = "10000001";

	private String Radom;

	private String UKey;

	private int PageIndex;

	private String Signatrue;

	public QuoteParam(String radom, String uKey, int pageIndex, String token) {
		super();
		Radom = radom;
		UKey = uKey;
		PageIndex = pageIndex;
		try {
			this.Signatrue = Md5Util.md5(token + ApiId + Radom + UKey + PageIndex);
		} catch (Exception e) {
			throw new RuntimeException("md5 exception!" + e.getMessage());
		}
	}

	public String getApiId() {
		return ApiId;
	}

	public void setApiId(String apiId) {
		ApiId = apiId;
	}

	public String getRadom() {
		return Radom;
	}

	public void setRadom(String radom) {
		Radom = radom;
	}

	public String getUKey() {
		return UKey;
	}

	public void setUKey(String uKey) {
		UKey = uKey;
	}

	public int getPageIndex() {
		return PageIndex;
	}

	public void setPageIndex(int pageIndex) {
		PageIndex = pageIndex;
	}

	public String getSignatrue() {
		return Signatrue;
	}

	public void setSignatrue(String signatrue) {
		Signatrue = signatrue;
	}

}
