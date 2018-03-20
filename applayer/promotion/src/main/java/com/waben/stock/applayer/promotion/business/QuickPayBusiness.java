package com.waben.stock.applayer.promotion.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waben.stock.applayer.promotion.payapi.paypal.config.PayPalConfig;
import com.waben.stock.applayer.promotion.payapi.paypal.config.RSAUtil;
import com.waben.stock.applayer.promotion.payapi.paypal.utils.HttpUtil;
import com.waben.stock.applayer.promotion.payapi.paypal.utils.LianLianRSA;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.WithdrawalsApplyDto;
import com.waben.stock.interfaces.enums.WithdrawalsApplyState;
import com.waben.stock.interfaces.exception.ServiceException;

@Service
public class QuickPayBusiness {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public WithdrawalsApplyBusiness applyBusiness;

	@Value("${spring.profiles.active}")
	private String activeProfile;
	
	private boolean isProd = true;
	
	@PostConstruct
	public void init() {
		if ("prod".equals(activeProfile)) {
			isProd = true;
		} else {
			isProd = false;
		}
	}
	
	public void payPalCSA(WithdrawalsApplyDto apply) {
		// 请求提现
		SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
		Map<String, String> map = new TreeMap<>();
		map.put("oid_partner", PayPalConfig.oid_partner);
		map.put("api_version", PayPalConfig.csa_version);
		map.put("sign_type", PayPalConfig.sign_type);
		map.put("no_order", apply.getApplyNo());
		map.put("dt_order", time.format(new Date()));
		map.put("money_order", apply.getAmount().toString());
		map.put("card_no", apply.getBankCard());
		map.put("acct_name", apply.getName());
		map.put("info_order", PayPalConfig.info_order);
		map.put("flag_card", PayPalConfig.flag_card);// 对私
		map.put("notify_url", isProd ? PayPalConfig.prod_csa_notifyurl : PayPalConfig.test_csa_notifyurl);
		map.put("memo", PayPalConfig.memo);

		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
		String tosign = genSignData(jsonObject);
		logger.info("代付签名的参数是:{}", tosign);
		String sign = RSAUtil.sign(PayPalConfig.private_key, tosign);
		map.put("sign", sign);
		logger.info("代付的参数是:{}", map.toString());
		logger.info("代付请求发起");
		JSONObject jsonObject1 = (JSONObject) JSONObject.toJSON(map);
		String sign1 = null;
		try {
			sign1 = LianLianRSA.encrypt(jsonObject1.toJSONString(), PayPalConfig.public_key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject response = new JSONObject();
		response.put("pay_load", sign1);
		response.put("oid_partner", PayPalConfig.oid_partner);
		logger.info("代付请求的参数是:{}", response.toJSONString());
		String result = HttpUtil.doPost(PayPalConfig.csa_url, response, "utf-8");
		JSONObject jsStr = JSONObject.parseObject(result);
		logger.info("代付请求的结果是:{}", jsStr.toJSONString());
		String confirm_code = jsStr.getString("confirm_code");
		// 如果被判定为 疑似重复提交订单则进行确认请求
		if (!StringUtils.isBlank(confirm_code)) {
			Map<String, String> confirmMap = new TreeMap<>();
			confirmMap.put("oid_partner", PayPalConfig.oid_partner);
			confirmMap.put("sign_type", PayPalConfig.sign_type);
			confirmMap.put("api_version", PayPalConfig.csa_version);
			confirmMap.put("no_order", apply.getApplyNo());
			confirmMap.put("confirm_code", confirm_code);
			map.put("notify_url", isProd ? PayPalConfig.prod_csa_notifyurl : PayPalConfig.test_csa_notifyurl);
			JSONObject confirmObject = (JSONObject) JSONObject.toJSON(confirmMap);
			String confirmToSign = genSignData(confirmObject);
			logger.info("确认提交签名的参数是:{}", confirmToSign);
			String confirmSign = RSAUtil.sign(PayPalConfig.private_key, confirmToSign);
			confirmMap.put("sign", confirmSign);
			logger.info("代付确认的参数是:{}", confirmMap.toString());
			logger.info("代付确认请求发起");
			JSONObject confirmObject1 = (JSONObject) JSONObject.toJSON(confirmMap);
			String confirmSign1 = null;
			try {
				confirmSign1 = LianLianRSA.encrypt(confirmObject1.toJSONString(), PayPalConfig.public_key);
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONObject confirmResponse = new JSONObject();
			confirmResponse.put("pay_load", confirmSign1);
			confirmResponse.put("oid_partner", PayPalConfig.oid_partner);
			logger.info("代付确认请求的参数是:{}", confirmResponse.toJSONString());
			String confirmResult = HttpUtil.doPost(PayPalConfig.csa_confirm_url, confirmResponse, "utf-8");
			jsStr = JSONObject.parseObject(confirmResult);
			logger.info("代付确认请求的结果是:{}", jsStr.toJSONString());

		}
		// 修改提现状态
		applyBusiness.changeState(apply.getId(), "0000".equals(jsStr.getString("ret_code")) ? WithdrawalsApplyState.PROCESSING.getIndex() : WithdrawalsApplyState.FAILURE.getIndex());
		// 如果请求失败 抛出异常
		if (!"0000".equals(jsStr.getString("ret_code"))) {
			throw new ServiceException(ExceptionConstant.WITHDRAWALS_EXCEPTION, jsStr.getString("ret_msg"));
		}
	}

	public void payPalWithholdCallback(String applyNo, WithdrawalsApplyState state, String thirdRespCode,
			String thirdRespMsg) {
		WithdrawalsApplyDto apply = applyBusiness.fetchByApplyNo(applyNo);
		applyBusiness.changeState(apply.getId(), state.getIndex());
	}
	
	public static String genSignData(com.alibaba.fastjson.JSONObject jsonObject) {
        StringBuffer content = new StringBuffer();

        // 按照key做首字母升序排列
        List<String> keys = new ArrayList<String>(jsonObject.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            if ("sign".equals(key)) {
                continue;
            }
            String value = jsonObject.getString(key);
            // 空串不参与签名
            if (isnull(value)) {
                continue;
            }
            content.append((i == 0 ? "" : "&") + key + "=" + value);

        }
        String signSrc = content.toString();
        if (signSrc.startsWith("&")) {
            signSrc = signSrc.replaceFirst("&", "");
        }
        return signSrc;
    }

    public static boolean isnull(String str) {
        if (null == str || str.equalsIgnoreCase("null") || str.equals("")) {
            return true;
        } else
            return false;
    }

}
