package com.waben.stock.applayer.strategist.business;

import com.alibaba.fastjson.JSONObject;
import com.waben.stock.applayer.strategist.payapi.czpay.bean.CzPayReturn;
import com.waben.stock.applayer.strategist.payapi.czpay.config.CzPayConfig;
import com.waben.stock.applayer.strategist.payapi.shande.bean.PayRequestBean;
import com.waben.stock.applayer.strategist.payapi.shande.config.SandPayConfig;
import com.waben.stock.applayer.strategist.payapi.shande.utils.FormRequest;
import com.waben.stock.applayer.strategist.reference.PaymentOrderReference;
import com.waben.stock.applayer.strategist.reference.PublisherReference;
import com.waben.stock.applayer.strategist.reference.WithdrawalsOrderReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.PaymentOrderDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.dto.publisher.WithdrawalsOrderDto;
import com.waben.stock.interfaces.enums.PaymentState;
import com.waben.stock.interfaces.enums.PaymentType;
import com.waben.stock.interfaces.enums.WithdrawalsState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.util.UniqueCodeGenerator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class QuickPayBusiness {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	@Qualifier("paymentOrderReference")
	private PaymentOrderReference paymentOrderReference;
	@Autowired
	@Qualifier("publisherReference")
	private PublisherReference publisherReference;
	@Autowired
	@Qualifier("withdrawalsOrderReference")
	private WithdrawalsOrderReference withdrawalsOrderReference;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	@Autowired
	private CapitalAccountBusiness accountBusiness;

	public PaymentOrderDto savePaymentOrder(PaymentOrderDto paymentOrder) {
		Response<PaymentOrderDto> orderResp = paymentOrderReference.addPaymentOrder(paymentOrder);
		if ("200".equals(orderResp.getCode())) {
			return orderResp.getResult();
		}
		throw new ServiceException(orderResp.getCode());
	}

	public Map<String, String> quickpay(BigDecimal amount, String phone) {

		Response<PublisherDto> response = publisherReference.fetchByPhone(phone);
		if (!"200".equals(response.getCode())) {
			throw new ServiceException(response.getCode());
		}
		// 创建订单
		PaymentOrderDto paymentOrder = new PaymentOrderDto();
		paymentOrder.setAmount(amount);
		String paymentNo = UniqueCodeGenerator.generatePaymentNo();
		paymentOrder.setPaymentNo(paymentNo);
		paymentOrder.setType(PaymentType.QuickPay);
		paymentOrder.setState(PaymentState.Unpaid);
		paymentOrder.setPublisherId(response.getResult().getId());
		paymentOrder.setCreateTime(new Date());
		paymentOrder.setUpdateTime(new Date());
		this.savePaymentOrder(paymentOrder);
		// 封装请求参数
		PayRequestBean request = new PayRequestBean();
		request.setUserId(phone.substring(1));
		request.setMchNo(SandPayConfig.mchNo);
		request.setMchType(SandPayConfig.mchType);
		request.setPayChannel(SandPayConfig.payChannel);
		request.setPayChannelTypeNo(SandPayConfig.payChannelTypeNo);
		request.setNotifyUrl(SandPayConfig.notifyUrl);
		request.setFrontUrl(SandPayConfig.fontUrl);
		request.setOrderNo(paymentNo);
		request.setAmount(amount.toString());
		request.setGoodsName(SandPayConfig.goodsName);
		request.setGoodsDesc(SandPayConfig.goodsDesc);
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStamp = format.format(date);
		request.setTimeStamp(timeStamp);
		Map<String, String> paramMap = (Map<String, String>) JacksonUtil.decode(JacksonUtil.encode(request), Map.class);
		TreeMap<String, String> sortParamMap = new TreeMap<>(paramMap);
		StringBuilder strForSign = new StringBuilder();
		String toSign = "";
		for (String key : sortParamMap.keySet()) {
			toSign += key + "=" + sortParamMap.get(key) + "&";
		}
		toSign += "key=" + SandPayConfig.key;
		System.out.println(toSign);
		String sign = DigestUtils.md5Hex(toSign);
		sortParamMap.put("sign", sign);
		return sortParamMap;
	}

	public String sdPayReturn() {
		String paymentNo = "";
		String stateStr = "";
		String code = "";
		try {
			return CzPayConfig.webReturnUrl + "?paymentNo" + paymentNo + "&code=" + code + "&message="
					+ URLEncoder.encode(stateStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("utf-8 not supported?");
		}
	}

	public String sdPaycallback(HttpServletRequest request) {
		Map<String, String> responseResult = paramter2Map(request);
		Map<String, String> resultMap = new TreeMap<>(responseResult);
		String toSign = "";
		for (String key : resultMap.keySet()) {
			if (!"msg".equals(key) && !"result".equals(key) && !"sign".equals(key)) {
				if (!StringUtils.isBlank(resultMap.get(key))) {
					toSign += key + "=" + resultMap.get(key) + "&";
				}
			}
		}
		toSign += "key=" + SandPayConfig.key;
		String resultSign = DigestUtils.md5Hex(toSign);
		String result = responseResult.get("result");
		String sign = responseResult.get("sign");
		String paymentNo = responseResult.get("orderNo");
		String thirdPaymentNo = responseResult.get("gwTradeNo");
		try {
			if (paymentNo != null && !"".equals(paymentNo) && "SUCCESS".equals(result)) {
				// 验证签名
				if (sign.equals(resultSign)) {
					// 支付成功
					logger.info("PC快捷异步通知签名验证通过");
					payCallback(paymentNo, PaymentState.Paid);
				}
				return "success";
			}
			return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><retcode>207267</retcode><retmsg></retmsg>支付失败</root>";
		} catch (Exception ex) {
			return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><retcode>207267</retcode><retmsg></retmsg>校验签名失败</root>";
		}
	}

	public void withdrawals(Long publisherId, BigDecimal amount, String name, String phone, String idCard,
			String bankCard, String bankCode, String branchName) {
		// 生成提现订单
		logger.info("保存提现订单");
		WithdrawalsOrderDto order = new WithdrawalsOrderDto();
		String withdrawalsNo = UniqueCodeGenerator.generateWithdrawalsNo();
		order.setWithdrawalsNo(withdrawalsNo);
		order.setAmount(amount);
		order.setState(WithdrawalsState.PROCESSING);
		order.setName(name);
		order.setIdCard(idCard);
		order.setBankCard(bankCard);
		order.setPublisherId(publisherId);
		order.setCreateTime(new Date());
		order.setUpdateTime(new Date());
		this.saveWithdrawalsOrder(order);
		// 请求提现
		SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
		Map<String, String> map = new TreeMap();
		map.put("mchNo", SandPayConfig.mchNo);
		map.put("payChannel", SandPayConfig.payChannel);
		map.put("orderNo", withdrawalsNo);
		map.put("amount", amount.toString());
		map.put("bankType", SandPayConfig.bankType);
		map.put("accNo", bankCard);
		map.put("accName", name);
		map.put("bankName", "中国银行");
		map.put("timeStamp", time.format(new Date()));
		// 签名
		String toSign = "";
		for (String key : map.keySet()) {
			toSign += key + "=" + map.get(key) + "&";
		}
		toSign += "key=" + SandPayConfig.key;
		logger.info("代付签名的参数是:{}", toSign);
		String sign = DigestUtils.md5Hex(toSign);
		map.put("sign", sign);
		logger.info("代付的参数是:{}", map.toString());
		logger.info("代付请求发起");
		String result = FormRequest.doPost(map, SandPayConfig.csaUrl);
		JSONObject jsStr = JSONObject.parseObject(result);
		System.out.println(jsStr.toString());
		logger.info("代付请求的结果是:{}", jsStr);
		// CzWithholdResponse resp =
		// CzWithholdOverSocket.withhold(withdrawalsNo, name, bankCard, phone,
		// bankCode, amount);
		// // 提现异常
		JSONObject jsonData = jsStr.getJSONObject("data");
		String resultFlag = jsonData.getString("resultFlag");
		if ("SUCCESS".equals(jsStr.getString("result")) || "0".equals(resultFlag) || "2".equals(resultFlag)) {
			WithdrawalsOrderDto origin = findWithdrawalsOrder(withdrawalsNo);
			accountBusiness.csa(origin.getPublisherId(), origin.getAmount(), order.getId());
			if (origin.getState() != WithdrawalsState.PROCESSED) {
				// 更新代付订单的状态
				withdrawalsOrderReference.changeState(withdrawalsNo, WithdrawalsState.PROCESSED.getIndex());
			}
		} else {
			throw new ServiceException(ExceptionConstant.WITHDRAWALS_EXCEPTION, jsStr.getString("msg"));
		}
	}

	private Map<String, String> paramter2Map(HttpServletRequest request) {
		Map<String, String> params = new HashMap<>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
			// "utf-8");
			params.put(name, valueStr);
		}
		return params;
	}

	public void payCallback(String paymentNo, PaymentState state) {
		PaymentOrderDto origin = findByPaymentNo(paymentNo);
		if (origin.getState() != state) {
			// 更新支付订单的状态
			changeState(paymentNo, state);
			// 给发布人账号中充值
			if (state == PaymentState.Paid) {
				accountBusiness.recharge(origin.getPublisherId(), origin.getAmount(), origin.getId());
			}
		}
	}

	public PaymentOrderDto findByPaymentNo(String paymentNo) {
		Response<PaymentOrderDto> orderResp = paymentOrderReference.fetchByPaymentNo(paymentNo);
		if ("200".equals(orderResp.getCode())) {
			return orderResp.getResult();
		}
		throw new ServiceException(orderResp.getCode());
	}

	public PaymentOrderDto changeState(String paymentNo, PaymentState state) {
		Response<PaymentOrderDto> orderResp = paymentOrderReference.changeState(paymentNo, state.getIndex());
		if ("200".equals(orderResp.getCode())) {
			return orderResp.getResult();
		}
		throw new ServiceException(orderResp.getCode());
	}

	public WithdrawalsOrderDto saveWithdrawalsOrder(WithdrawalsOrderDto withdrawalsOrderDto) {
		Response<WithdrawalsOrderDto> orderResp = withdrawalsOrderReference.saveWithdrawalsOrders(withdrawalsOrderDto,
				withdrawalsOrderDto.getWithdrawalsNo());
		if ("200".equals(orderResp.getCode())) {
			return orderResp.getResult();
		}
		throw new ServiceException(orderResp.getCode());
	}

	public WithdrawalsOrderDto findWithdrawalsOrder(String withdrawalsNo) {
		Response<WithdrawalsOrderDto> orderResp = withdrawalsOrderReference.fetchByWithdrawalsNo(withdrawalsNo);
		if ("200".equals(orderResp.getCode())) {
			return orderResp.getResult();
		}
		throw new ServiceException(orderResp.getCode());
	}
}
