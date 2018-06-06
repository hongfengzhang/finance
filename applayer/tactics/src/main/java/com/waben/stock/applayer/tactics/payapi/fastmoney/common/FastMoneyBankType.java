package com.waben.stock.applayer.tactics.payapi.fastmoney.common;

import java.util.HashMap;
import java.util.Map;

import com.waben.stock.interfaces.enums.BankType;
import com.waben.stock.interfaces.enums.CommonalityEnum;

public enum FastMoneyBankType implements CommonalityEnum {

	GSYH("1", "1", "中国工商银行", BankType.GSYH),

	ZSYH("2", "2", "招商银行", BankType.ZSYH),

	JSYH("3", "3", "中国建设银行", BankType.JSYH),

	NYYH("4", "4", "中国农业银行", BankType.NYYH),

	ZGYH("5", "5", "中国银行", BankType.ZGYH),

	PFYH("6", "6", "上海浦东发展银行", BankType.PFYH),

	JTYH("7", "7", "交通银行", BankType.JTYH),

	MSYH("8", "8", "中国民生银行", BankType.MSYH),

	SZFZYH("9", "9", "深圳发展银行", BankType.SZFZYH),

	GFYH("10", "10", "广东发展银行", BankType.GFYH),

	ZXYH("11", "11", "中信银行", BankType.ZXYH),

	HXYH("12", "12", "华夏银行", BankType.HXYH),

	XYYH("13", "13", "兴业银行", BankType.XYYH),

	YZCX("14", "14", "中国邮政储蓄", BankType.YZCX),

	GDYH("15", "15", "中国光大银行", BankType.GDYH),

	BHYH("16", "16", "渤海银行", BankType.BHYH),

	// 部分银行card_bin有别名的银行
	GSYH1("17", "17", "中国工商银行", BankType.GSYH1),

	JSYH1("18", "18", "中国建设银行", BankType.JSYH1),

	YZCX1("19", "19", "中国邮政储蓄", BankType.YZCX1),

	GFYH1("20", "20", "广东发展银行", BankType.GFYH1),

	PFYH1("21", "21", "上海浦东发展银行", BankType.PFYH1);

	private String index;

	private String code;

	private String bank;

	private BankType plateformBankType;

	private FastMoneyBankType(String index, String code, String bank, BankType plateformBankType) {
		this.index = index;
		this.code = code;
		this.bank = bank;
		this.plateformBankType = plateformBankType;
	}

	private static Map<BankType, FastMoneyBankType> map = new HashMap<BankType, FastMoneyBankType>();

	static {
		for (FastMoneyBankType _enum : FastMoneyBankType.values()) {
			map.put(_enum.getPlateformBankType(), _enum);
		}
	}

	public static FastMoneyBankType getByPlateformBankType(BankType plateformBankType) {
		return map.get(plateformBankType);
	}

	/*********************** setter and getter **************************/

	public String getIndex() {
		return index;
	}

	public String getBank() {
		return bank;
	}

	public String getCode() {
		return code;
	}

	public BankType getPlateformBankType() {
		return plateformBankType;
	}

}
