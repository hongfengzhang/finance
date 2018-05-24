package com.waben.stock.futuresgateway.twsapi;

public class TwsConstant {

	/**
	 * 行情快照tickerId
	 */
	public static final String MarketSnapshot_TickerId_Prefix = "100";
	/**
	 * 行情推送tickerId
	 */
	public static final String MarketPush_TickerId_Prefix = "200";
	/**
	 * 分时图tickerId
	 */
	public static final String TimeLine_TickerId_Prefix = "300";
	public static final String TimeLine_RedisKey = "futures:market:timeline:%s";
	/**
	 * 日K线tickerId
	 */
	public static final String DayLine_TickerId_Prefix = "400";
	public static final String DayLine_RedisKey = "futures:market:dayline:%s";
	/**
	 * 1分钟K线tickerId
	 */
	public static final String Min1Line_TickerId_Prefix = "500";
	public static final String Min1Line_RedisKey = "futures:market:min1line:%s";
	/**
	 * 3分钟K线tickerId
	 */
	public static final String Mins3Line_TickerId_Prefix = "600";
	public static final String Mins3Line_RedisKey = "futures:market:mins3line:%s";
	/**
	 * 5分钟K线tickerId
	 */
	public static final String Mins5Line_TickerId_Prefix = "700";
	public static final String Mins5Line_RedisKey = "futures:market:mins5line:%s";
	/**
	 * 15分钟K线tickerId
	 */
	public static final String Mins15Line_TickerId_Prefix = "800";
	public static final String Mins15Line_RedisKey = "futures:market:mins15line:%s";

}
