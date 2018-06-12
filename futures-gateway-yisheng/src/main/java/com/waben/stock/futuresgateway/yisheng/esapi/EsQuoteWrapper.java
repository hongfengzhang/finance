package com.waben.stock.futuresgateway.yisheng.esapi;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.future.api.es.external.common.bean.TapAPIContract;
import com.future.api.es.external.quote.QuoteApi;
import com.future.api.es.external.quote.bean.TapAPIQuotLoginRspInfo;
import com.future.api.es.external.quote.bean.TapAPIQuoteCommodityInfo;
import com.future.api.es.external.quote.bean.TapAPIQuoteContractInfo;
import com.future.api.es.external.quote.bean.TapAPIQuoteLoginAuth;
import com.future.api.es.external.quote.bean.TapAPIQuoteWhole;
import com.future.api.es.external.quote.listener.QuoteApiListener;
import com.future.api.es.external.util.JacksonUtil;
import com.waben.stock.futuresgateway.yisheng.cache.RedisCache;
import com.waben.stock.futuresgateway.yisheng.rabbitmq.RabbitmqConfiguration;
import com.waben.stock.futuresgateway.yisheng.rabbitmq.RabbitmqProducer;
import com.waben.stock.futuresgateway.yisheng.rabbitmq.message.TapAPIQuoteContractInfoWithSession;
import com.waben.stock.futuresgateway.yisheng.util.CopyBeanUtils;

/**
 * 易盛行情
 * 
 * @author luomengan
 *
 */
@Component
public class EsQuoteWrapper implements QuoteApiListener {

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

	/**
	 * 行情api
	 */
	private QuoteApi api;

	@Autowired
	private RabbitmqProducer rabbitmqProducer;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		api = new QuoteApi(quoteAuthCode, "", true);
		api.setHostAddress(quoteIp, quotePort);
		api.setApiListener(this);
		connect();
	}

	/**
	 * 连接
	 */
	public void connect() {
		api.login(new TapAPIQuoteLoginAuth(quoteUsername, 'N', quotePassword, null, null, 'N', null));
	}

	/************************** 以下方法为setter和getter ***********************/

	public QuoteApi getApi() {
		return api;
	}

	/************************** 以下方法为回调方法 ***********************/

	@Override
	public void onAPIReady() {
		// api连接成功后，查询所有品种
		api.qryCommodity();
	}

	@Override
	public void onDisconnected(int reasonCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRspLogin(int errorCode, TapAPIQuotLoginRspInfo info) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRspQryCommodity(int sessionID, int errorCode, boolean isLast, TapAPIQuoteCommodityInfo info) {
		rabbitmqProducer.sendMessage(RabbitmqConfiguration.commodityQueueName, info);
	}

	@Override
	public void onRspQryContract(int sessionID, int errorCode, boolean isLast, TapAPIQuoteContractInfo info) {
		TapAPIQuoteContractInfoWithSession sessionInfo = CopyBeanUtils
				.copyBeanProperties(TapAPIQuoteContractInfoWithSession.class, info, false);
		sessionInfo.setSessionID(sessionID);
		rabbitmqProducer.sendMessage(RabbitmqConfiguration.contractQueueName, sessionInfo);
	}

	@Override
	public void onRspSubscribeQuote(int sessionID, int errorCode, boolean isLast, TapAPIQuoteWhole quoteWhole) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRspUnSubscribeQuote(int sessionID, int errorCode, boolean isLast, TapAPIContract info) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRtnQuote(TapAPIQuoteWhole info) {
		// TODO Auto-generated method stub

	}

}
