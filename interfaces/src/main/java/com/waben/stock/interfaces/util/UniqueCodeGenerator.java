package com.waben.stock.interfaces.util;

import java.util.UUID;

/**
 * 唯一编码生成器
 * 
 * <p>
 * 生成规则后续再确定，一下生成规则都是暂用
 * </p>
 * 
 * @author luomengan
 *
 */
public class UniqueCodeGenerator {

	/**
	 * 生成序列号
	 * 
	 * @return 序列号
	 */
	public static String generateSerialCode() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 生成交易单号
	 * 
	 * @return 交易单号
	 */
	public static String generateTradeNo() {
		return String.valueOf(System.currentTimeMillis());
	}

}
