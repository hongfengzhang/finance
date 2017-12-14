package com.waben.stock.applayer.strategist.business;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.strategist.dto.payment.PayRequest;
import com.waben.stock.applayer.strategist.dto.payment.PayResponse;
import com.waben.stock.applayer.strategist.service.PaymentOrderService;
import com.waben.stock.applayer.strategist.tfbpay.TfbPayOverHttp;
import com.waben.stock.applayer.strategist.tfbpay.util.RSAUtils;
import com.waben.stock.applayer.strategist.tfbpay.util.RequestUtils;
import com.waben.stock.interfaces.dto.publisher.PaymentOrderDto;
import com.waben.stock.interfaces.enums.PaymentState;
import com.waben.stock.interfaces.enums.PaymentType;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.UniqueCodeGenerator;

@Service
public class PaymentBusiness {

	@Autowired
	private PaymentOrderService paymentOrderService;

	@Autowired
	private CapitalAccountBusiness accountBusiness;

	public String recharge(Long publisherId, PayRequest payReq) {
		payReq.setPaymentNo(UniqueCodeGenerator.generatePaymentNo());
		// 请求第三方支付
		String result = payment(payReq);
		// 保存支付订单
		PaymentOrderDto paymentOrder = new PaymentOrderDto();
		paymentOrder.setAmount(payReq.getAmount());
		paymentOrder.setPaymentNo(payReq.getPaymentNo());
		paymentOrder.setPublisherId(publisherId);
		paymentOrder.setType(payReq.getPaymentType());
		paymentOrder.setState(PaymentState.Unpaid);
		this.save(paymentOrder);
		return result;
	}

	public PayResponse withdrawals(Long publisherId, String publisherSerialCode, PayRequest payReq) {
		// TODO
		return null;
	}

	public PaymentOrderDto save(PaymentOrderDto paymentOrder) {
		Response<PaymentOrderDto> orderResp = paymentOrderService.addPaymentOrder(paymentOrder);
		if ("200".equals(orderResp.getCode())) {
			return orderResp.getResult();
		}
		throw new ServiceException(orderResp.getCode());
	}

	public PaymentOrderDto findByPaymentNo(String paymentNo) {
		Response<PaymentOrderDto> orderResp = paymentOrderService.fetchByPaymentNo(paymentNo);
		if ("200".equals(orderResp.getCode())) {
			return orderResp.getResult();
		}
		throw new ServiceException(orderResp.getCode());
	}

	public PaymentOrderDto changeState(String paymentNo, PaymentState state) {
		Response<PaymentOrderDto> orderResp = paymentOrderService.changeState(paymentNo, state.getIndex());
		if ("200".equals(orderResp.getCode())) {
			return orderResp.getResult();
		}
		throw new ServiceException(orderResp.getCode());
	}

	private String payment(PayRequest payReq) {
		if (payReq.getPaymentType() == PaymentType.UnionPay) {
			try {
				// 请求第三方支付
				return TfbPayOverHttp.payment(payReq.getPaymentNo(), payReq.getAmount());
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("not supported ecodeding?");
			}
		} else {
			return null;
		}
	}

	public String tbfPaycallback(String cipherData) {
		try {
			// 解码
			String data = RSAUtils.decrypt(cipherData);
			HashMap<String, String> dataMap = RequestUtils.parseString(data);
			// 验签
			boolean isSuccess = RSAUtils.verify(data, dataMap.get("sign"));
			if (!isSuccess) {
				return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><retcode>207267</retcode><retmsg></retmsg>校验签名失败</root>";
			}

			String paymentNo = dataMap.get("spbillno");
			String state = dataMap.get("result");
			if (paymentNo != null && !"".equals(paymentNo) && "1".equals(state)) {
				// 支付成功
				payCallback(paymentNo, PaymentState.Paid);
			}
			return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><retcode>00</retcode></root>";
		} catch (Exception ex) {
			return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><retcode>207267</retcode><retmsg></retmsg>校验签名失败</root>";
		}
	}

	public String tbfPayReturn(String cipherData) {
		StringBuilder result = new StringBuilder();
		result.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>回调页面</title></head><body>");
		String paymentNo = "";
		String stateStr = "";
		String scriptContent = "<script>function call() {window.webkit.messageHandlers.callback.postMessage({paymentNo:'%s',result:'%s'});}</script>";
		String bodyContent = "<p>%s!</p>";
		try {
			// 解码
			String data = RSAUtils.decrypt(cipherData);
			HashMap<String, String> dataMap = RequestUtils.parseString(data);
			// 验签
			boolean isSuccess = RSAUtils.verify(data, dataMap.get("sign"));
			if (isSuccess) {
				paymentNo = dataMap.get("spbillno");
				String state = dataMap.get("result");
				stateStr = "1".equals(state) ? "支付成功" : "支付异常，请稍后重试或调接口查询结果";
			} else {
				stateStr = "验签失败";
			}
		} catch (Exception ex) {
			stateStr = "验签失败";
		}

		result.append(String.format(scriptContent, paymentNo, stateStr));
		result.append(String.format(bodyContent, stateStr));
		result.append("<button type=\"button\" id=\"myBtn\" onclick=\"call()\">返回APP</button></body></html>");
		return result.toString();
	}

	public void payCallback(String paymentNo, PaymentState state) {
		PaymentOrderDto origin = findByPaymentNo(paymentNo);
		if (origin.getState() != state) {
			// 更新支付订单的状态
			changeState(paymentNo, state);
			// 给发布人账号中充值
			if (state == PaymentState.Paid) {
				accountBusiness.recharge(origin.getPublisherId(), origin.getAmount());
			}
		}
	}

}
