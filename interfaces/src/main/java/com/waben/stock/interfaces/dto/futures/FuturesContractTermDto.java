package com.waben.stock.interfaces.dto.futures;

import java.util.Date;

public class FuturesContractTermDto {

	private Long id;
	/**
	 * 对应的期货合约
	 */
	private FuturesContractDto contract;
	/**
	 * 是否为当前实施的期限
	 */
	private boolean current;
	/**
	 * The first day on which a notice of intent to deliver a commodity can be
	 * made by a clearinghouse to a buyer in order to fulfill a given futures
	 * contract
	 */
	private Date firstNoticeDate;
	/**
	 * The first day when an invesor who is short a commodify futures contract
	 * may notify the clearinghouse of the intention to deliver the commodity
	 */
	private Date firstPositonDate;
	/**
	 * 最后交易日
	 */
	private Date lastTradingDate;
	/**
	 * 到期日期
	 */
	private Date expirationDate;
	/**
	 * 周一交易时间(交易所)
	 * <p>
	 * 时间段用“-”隔开，多个时间段用“,”隔开，如“18:00-23:59”、“00:00-17:00,18:00-23:59”
	 * <p>
	 */
	private String monTradeTime;
	/**
	 * 周一交易时间描述
	 */
	private String monTradeTimeDesc;
	/**
	 * 周二交易时间(交易所)
	 */
	private String tueTradeTime;
	/**
	 * 周二交易时间描述
	 */
	private String tueTradeTimeDesc;
	/**
	 * 周三交易时间(交易所)
	 */
	private String wedTradeTime;
	/**
	 * 周三交易时间描述
	 */
	private String wedTradeTimeDesc;
	/**
	 * 周四交易时间(交易所)
	 */
	private String thuTradeTime;
	/**
	 * 周四交易时间描述
	 */
	private String thuTradeTimeDesc;
	/**
	 * 周五交易时间(交易所)
	 */
	private String friTradeTime;
	/**
	 * 周五交易时间描述
	 */
	private String friTradeTimeDesc;
	/**
	 * 周六交易时间(交易所)
	 */
	private String satTradeTime;
	/**
	 * 周六交易时间描述
	 */
	private String satTradeTimeDesc;
	/**
	 * 周日交易时间(交易所)
	 */
	private String sunTradeTime;
	/**
	 * 周日交易时间描述
	 */
	private String sunTradeTimeDesc;
}
