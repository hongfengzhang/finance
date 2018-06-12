package com.waben.stock.futuresgateway.yisheng.esapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 易盛交易
 * 
 * @author luomengan
 *
 */
@Component
public class EsTradeWrapper {

	/** 交易IP */
	@Value("${es.trade.ip}")
	private String tradeIp;
	/** 交易端口 */
	@Value("${es.trade.port}")
	private short tradePort;
	/** 交易用户名 */
	@Value("${es.trade.username}")
	private String tradeUsername;
	/** 交易密码 */
	@Value("${es.trade.password}")
	private String tradePassword;
	/** 交易Token */
	@Value("${es.trade.authcode}")
	private String tradeAuthCode;

}
