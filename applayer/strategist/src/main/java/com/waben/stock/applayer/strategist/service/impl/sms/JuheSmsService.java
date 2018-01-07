package com.waben.stock.applayer.strategist.service.impl.sms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waben.stock.applayer.strategist.reference.PublisherReference;
import com.waben.stock.applayer.strategist.service.SmsCache;
import com.waben.stock.applayer.strategist.service.SmsService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.enums.SmsType;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

@Service("juheSmsService")
public class JuheSmsService implements SmsService {

	private static final String ServerUrl = "http://v.juhe.cn/sms/send";

	private static final String DataType = "json";

	private static final String Key = "b54e74f38026953caa0ffa3f6480108d";

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	@Qualifier("publisherReference")
	private PublisherReference publisherReference;

	public void sendMessage(SmsType smsType, String phone, List<String> paramValues) {
		// 检查手机号
		Response<PublisherDto> publisherResp = publisherReference.fetchByPhone(phone);
		if (!"200".equals(publisherResp.getCode())
				&& !ExceptionConstant.DATANOTFOUND_EXCEPTION.equals(publisherResp.getCode())) {
			throw new ServiceException(publisherResp.getCode());
		}
		if (publisherResp.getResult() != null && smsType == SmsType.RegistVerificationCode) {
			throw new ServiceException(ExceptionConstant.PHONE_BEEN_REGISTERED_EXCEPTION);
		}
		if (publisherResp.getResult() == null && smsType == SmsType.ModifyPasswordCode) {
			throw new ServiceException(ExceptionConstant.PHONE_ISNOT_REGISTERED_EXCEPTION);
		}
		// 检查发送条件是否满足
		SmsCache.checkSendCondition(smsType, phone);
		// 发送短信
		try {
			// 组装请求参数
			Map<String, Object> params = new HashMap<String, Object>();
			JuheSmsTemplate template = JuheSmsTemplate.getBySmsType(smsType);
			params.put("dtype", DataType);
			params.put("mobile", phone);
			params.put("tpl_id", template.getTemplateCode());
			StringBuilder tplValue = new StringBuilder();
			String[] paramNames = template.getParamNames();
			if (paramNames != null && paramNames.length > 0) {
				for (int i = 0; i < paramNames.length; i++) {
					tplValue.append("#" + paramNames[i] + "#=" + paramValues.get(i));
					if (i != paramNames.length - 1) {
						tplValue.append("&");
					}
				}
			}
			params.put("tpl_value", URLEncoder.encode(tplValue.toString(), "UTF-8"));
			params.put("key", Key);
			StringBuilder urlParam = new StringBuilder("?");
			int j = 0;
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				urlParam.append(entry.getKey() + "=" + entry.getValue());
				if (j != params.size() - 1) {
					urlParam.append("&");
				}
				j++;
			}
			// 请求发送短信
			logger.info("request juhe sms url: {}", ServerUrl + urlParam.toString());
			String response = restTemplate.getForObject(ServerUrl + urlParam.toString(), String.class);
			logger.info("juhe sms response: {}", response);
			// 处理发送结果
			JuheResponseBean responseBean = objectMapper.readValue(response, JuheResponseBean.class);
			if (responseBean.getError_code() == 0) {
				if (smsType == SmsType.RegistVerificationCode || smsType == SmsType.ModifyPasswordCode
						|| smsType == SmsType.BindCardCode || smsType == SmsType.ModifyPaymentPwdCode) {
					SmsCache.cache(smsType, phone, paramValues.get(0));
				}
			} else if (responseBean.getError_code() == 205401) {
				throw new ServiceException(ExceptionConstant.PHONE_WRONG_EXCEPTION);
			} else {
				throw new ServiceException(ExceptionConstant.SENDMESSAGE_FAILED_EXCEPTION);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ExceptionConstant.SENDMESSAGE_FAILED_EXCEPTION);
		} catch (JsonParseException | JsonMappingException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ExceptionConstant.SENDMESSAGE_FAILED_EXCEPTION);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(ExceptionConstant.SENDMESSAGE_FAILED_EXCEPTION);
		}
	}

	public static class JuheResponseBean {

		private int error_code;

		private String reason;

		public int getError_code() {
			return error_code;
		}

		public void setError_code(int error_code) {
			this.error_code = error_code;
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

	}

}
