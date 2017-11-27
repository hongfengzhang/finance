package com.waben.stock.interfaces.util;

import java.util.UUID;

/**
 * 序列号生成器
 * 
 * @author luomengan
 *
 */
public class SerialCodeGenerator {

	public static String generate() {
		return UUID.randomUUID().toString();
	}

}
