package com.waben.stock.interfaces.commonapi.netease;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Im即时通讯统一请求网易云
 * 
 * @author luomengan
 *
 */
public class ImOverHttps {
	
	public static RestTemplate restTemplate = new RestTemplate();

	private static Logger logger = LoggerFactory.getLogger(ChannelManageOverHttps.class);
	
}
