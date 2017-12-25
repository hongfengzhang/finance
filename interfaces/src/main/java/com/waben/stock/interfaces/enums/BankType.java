package com.waben.stock.interfaces.enums;

import java.util.HashMap;
import java.util.Map;

public enum BankType implements CommonalityEnum {

	RMYH("1", "001", "中国人民银行"),

	GSYH("2", "102", "中国工商银行"),

	NYYH("3", "103", "中国农业银行"),

	ZGYH("4", "104", "中国银行"),

	JSYH("5", "105", "中国建设银行"),

	NYFZYH("6", "203", "中国农业发展银行"),

	JTYH("7", "301", "交通银行"),

	ZXYH("8", "302", "中信银行"),

	GDYH("9", "303", "中国光大银行"),

	HXYH("10", "304", "华夏银行"),

	MSYH("11", "305", "中国民生银行"),

	GFYH("12", "306", "广东发展银行"),

	SZFZYH("13", "307", "深圳发展银行"),

	ZSYH("14", "308", "招商银行"),

	XYYH("15", "309", "兴业银行"),

	PFYH("16", "310", "上海浦东发展银行"),

	CSSYYH("17", "313", "城市商业银行"),

	NCSYYH("18", "314", "农村商业银行"),

	HFYH("19", "315", "恒丰银行"),

	NCHZYH("20", "317", "农村合作银行"),

	BHYH("21", "318", "渤海银行"),

	YZCX("22", "403", "中国邮政储蓄银行"),

	PAYH("23", "783", "平安银行"),

	QDGJYH("24", "786", "青岛国际银行");

	private String index;

	private String bank;

	private String code;

	private BankType(String index, String code, String bank) {
		this.index = index;
		this.code = code;
		this.bank = bank;
	}

	private static Map<String, BankType> nameMap = new HashMap<String, BankType>();

	private static Map<String, BankType> codeMap = new HashMap<String, BankType>();

	static {
		for (BankType _enum : BankType.values()) {
			nameMap.put(_enum.getBank(), _enum);
			codeMap.put(_enum.getCode(), _enum);
		}
	}

	public static BankType getByBank(String bank) {
		return nameMap.get(bank);
	}

	public static BankType getByCode(String code) {
		return codeMap.get(code);
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

}
