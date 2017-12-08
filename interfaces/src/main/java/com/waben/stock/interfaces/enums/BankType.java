package com.waben.stock.interfaces.enums;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public enum BankType implements CommonalityEnum {

	DEFAULT("1", "unknow_001", "默认银行", "/bankIcon/default@3x.png"),

	BJYH("2", "unknow_002", "北京银行", "/bankIcon/bjyh@3x.png"),

	GSYH("3", "102", "工商银行", "/bankIcon/gsyh@3x.png"),

	GDYH("4", "303", "光大银行", "/bankIcon/gdyh@3x.png"),

	GFYH("5", "306", "广发银行", "/bankIcon/gfyh@3x.png"),

	HXYH("6", "304", "华夏银行", "/bankIcon/hxyh@3x.png"),

	JSYH("7", "105", "建设银行", "/bankIcon/jsyh@3x.png"),

	JTYH("8", "301", "交通银行", "/bankIcon/jtyh@3x.png"),

	MSYH("9", "305", "民生银行", "/bankIcon/msyh@3x.png"),

	NYYH("10", "103", "农业银行", "/bankIcon/nyyh@3x.png"),

	PAYH("11", "783", "平安银行", "/bankIcon/payh@3x.png"),

	PFYH("12", "310", "浦发银行", "/bankIcon/pfyh@3x.png"),

	XYYH("13", "309", "兴业银行", "/bankIcon/xyyh@3x.png"),

	YZCX("14", "403", "邮政储蓄", "/bankIcon/yzcx@3x.png"),

	ZSYH("15", "308", "招商银行", "/bankIcon/zsyh@3x.png"),

	ZGXH("16", "unknow_003", "中国信合@", "/bankIcon/zgxh@3x.png"),

	ZGYH("17", "104", "中国银行", "/bankIcon/zgyh@3x.png"),

	ZXYH("18", "302", "中信银行", "/bankIcon/zxyh@3x.png");

	public static final String bankIconBaseLink = "http://10.0.0.99:8083/tactics";

	private String index;

	private String bank;

	private String code;

	private String iconLink;

	private BankType(String index, String code, String bank, String iconLink) {
		this.index = index;
		this.code = code;
		this.bank = bank;
		this.iconLink = iconLink;
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
		BankType result = nameMap.get(bank);
		return result != null ? result : DEFAULT;
	}

	public static BankType getByCode(String code) {
		BankType result = codeMap.get(code);
		return result != null ? result : DEFAULT;
	}

	/*********************** setter and getter **************************/

	public String getIndex() {
		return index;
	}

	public String getBank() {
		return bank;
	}

	public String getIconLink() {
		return bankIconBaseLink + iconLink;
	}

	public String getCode() {
		return code;
	}

}
