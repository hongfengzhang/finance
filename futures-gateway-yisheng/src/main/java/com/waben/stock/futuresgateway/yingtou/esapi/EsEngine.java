package com.waben.stock.futuresgateway.yingtou.esapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EsEngine {
	
	/** 行情IP */
	@Value("${es.quote.ip}")
	private String quoteIp;
	/** 行情端口 */
	@Value("${es.quote.port}")
	private short quotePort;
	/** 行情用户名 */
	@Value("${es.quote.username}")
	private String quoteUsername;
	/** 行情密码 */
	@Value("${es.quote.password}")
	private String quotePassword;
	/** 行情Token */
	@Value("${es.quote.authcode}")
	private String quoteAuthCode;
	
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
