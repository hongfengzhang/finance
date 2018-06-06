package com.waben.stock.applayer.tactics.payapi.fastmoney;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.waben.stock.applayer.tactics.payapi.fastmoney.bean.BankRequestBean;
import com.waben.stock.applayer.tactics.payapi.fastmoney.bean.BankResponseBean;
import com.waben.stock.applayer.tactics.payapi.fastmoney.bean.PayRequestBean;
import com.waben.stock.applayer.tactics.payapi.fastmoney.bean.QueryRequestBean;
import com.waben.stock.applayer.tactics.payapi.fastmoney.bean.QueryResponseBean;
import com.waben.stock.applayer.tactics.payapi.fastmoney.utils.BatchPayWS.BatchPay;
import com.waben.stock.applayer.tactics.payapi.fastmoney.utils.BatchPayWS.BatchPayServiceLocator;
import com.waben.stock.applayer.tactics.payapi.wabenpay.WabenPayOverHttp;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.util.RequestParamBuilder;

public class FastMoneyOverHttp {

	public static final String payFormAction = "https://www.99bill.com/gateway/recvMerchantInfoAction.htm";

	private static final Logger logger = LoggerFactory.getLogger(WabenPayOverHttp.class);

	/**
	 * 支付
	 * <p>
	 * 网关和快捷支付请求一样，只是商户号不一样
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public static String pay(PayRequestBean req) {
		try {
			// 初始部分参数
			req.setFormAction(payFormAction);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			req.setOrderTime(sdf.format(new Date()));
			// 获取签名字符串
			Map<String, Object> signMap = new HashMap<>();
			signMap.put("inputCharset", req.getInputCharset());
			signMap.put("pageUrl", req.getPageUrl());
			signMap.put("bgUrl", req.getBgUrl());
			signMap.put("version", req.getVersion());
			signMap.put("language", req.getLanguage());
			signMap.put("signType", req.getSignType());
			signMap.put("merchantAcctId", req.getMerchantAcctId());
			signMap.put("orderId", req.getOrderId());
			signMap.put("orderAmount", req.getOrderAmount());
			signMap.put("orderTime", req.getOrderTime());
			signMap.put("payType", req.getPayType());
			String signMsg = sign(RequestParamBuilder.build(signMap));
			req.setSignMsg(signMsg);
			// 获取支付页面的表单html
			String str = IOUtils.toString(
					FastMoneyOverHttp.class.getClassLoader().getResourceAsStream("fastmoney/pay.html"), "UTF-8");
			Map<String, String> paramMap = (Map<String, String>) JacksonUtil.decode(JacksonUtil.encode(req), Map.class);
			for (Entry<String, String> entry : paramMap.entrySet()) {
				str = str.replaceAll("\\{" + entry.getKey() + "\\}", entry.getValue() != null ? entry.getValue() : "");
			}
			return str;
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException("请求快钱网关支付发生异常!", ex);
		}
	}

	public static BankResponseBean withdraw(BankRequestBean req, String merchant, String key) {
		try {
			String mac = DigestUtils
					.md5Hex(req.getBankCardNumber() + req.getAmount() + req.getOrderId() + key.toUpperCase());
			req.setMac(mac);
			BankRequestBean[] list = { req };
			BatchPayServiceLocator service = new BatchPayServiceLocator();
			BatchPay batchPay = service.getBatchPayWS();
			InetAddress addr = null;
			addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress();// 获得本机IP
			BankResponseBean[] responseBeans = batchPay.bankPay(list, merchant, ip);
			logger.info("快钱代付请求的结果是:{}", JacksonUtil.encode(responseBeans));
			if (responseBeans.length < 1) {
				logger.error("快钱通道异常?返回结果为空");
				BankResponseBean response = new BankResponseBean();
				response.setResultFlag(false);
				return response;
			}
			BankResponseBean bankResponseBean = responseBeans[0];
			if (bankResponseBean.isResultFlag()) {
				logger.info("快钱代付通道处理中,已发往银行!");
				return bankResponseBean;
			} else {
				logger.info("快钱代付失败!");
				return bankResponseBean;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("请求快钱代付发生异常!", ex);
		}
	}

	public static QueryResponseBean withdrawQuery(String withdrawNo, String merchant, String key) {
		try {
			QueryRequestBean queryRequestBean = new QueryRequestBean();
			queryRequestBean.setQueryType("bankPay");
			queryRequestBean.setDealId("0");
			queryRequestBean.setOrderId(withdrawNo);
			BatchPayServiceLocator service = new BatchPayServiceLocator();
			BatchPay batchPay = service.getBatchPayWS();
			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress();// 获得本机IP
			QueryResponseBean[] queryResponseBeans = batchPay.queryDeal(queryRequestBean, merchant, ip);
			logger.info("快钱代付查询请求的结果是:{}", JacksonUtil.encode(queryResponseBeans));
			if (queryResponseBeans.length < 1) {
				logger.error("快钱查询异常?返回结果为空");
				return null;
			}
			QueryResponseBean queryResponseBean = queryResponseBeans[0];
			if (queryResponseBean.isResultFlag()) {
				logger.info("快钱查询成功!");
				return queryResponseBean;
			} else {
				logger.info("快钱查询失败!");
				return queryResponseBean;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("请求快钱代付查询发生异常!", ex);
		}
	}

	@SuppressWarnings("restriction")
	public static String sign(String signStr) {
		try {
			// 密钥仓库
			KeyStore ks = KeyStore.getInstance("PKCS12");
			InputStream is = FastMoneyOverHttp.class.getResourceAsStream("/fastmoney/99bill-rsa.pfx");
			BufferedInputStream ksbufin = new BufferedInputStream(is);
			char[] keyPwd = "zhidian".toCharArray();
			// char[] keyPwd = "YaoJiaNiLOVE999Year".toCharArray();
			ks.load(ksbufin, keyPwd);
			// 从密钥仓库得到私钥
			PrivateKey priK = (PrivateKey) ks.getKey("test-alias", keyPwd);
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initSign(priK);
			signature.update(signStr.getBytes("utf-8"));
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			return encoder.encode(signature.sign());
		} catch (FileNotFoundException e) {
			throw new RuntimeException("块钱签名需要的钥匙文件找不到!");
		} catch (Exception ex) {
			throw new RuntimeException("块钱签名发生异常!");
		}
	}

}
