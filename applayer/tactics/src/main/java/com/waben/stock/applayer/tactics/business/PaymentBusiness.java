package com.waben.stock.applayer.tactics.business;

import java.math.BigDecimal;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.tactics.czpay.CzPayOverHttp;
import com.waben.stock.applayer.tactics.czpay.CzWithholdOverSocket;
import com.waben.stock.applayer.tactics.czpay.bean.CzPayCallback;
import com.waben.stock.applayer.tactics.czpay.bean.CzPayResponse;
import com.waben.stock.applayer.tactics.czpay.bean.CzPayReturn;
import com.waben.stock.applayer.tactics.czpay.bean.CzWithholdResponse;
import com.waben.stock.applayer.tactics.dto.payment.PayRequest;
import com.waben.stock.applayer.tactics.service.PaymentOrderService;
import com.waben.stock.applayer.tactics.service.WithdrawalsOrderService;
import com.waben.stock.applayer.tactics.tfbpay.util.RSAUtils;
import com.waben.stock.applayer.tactics.tfbpay.util.RequestUtils;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.PaymentOrderDto;
import com.waben.stock.interfaces.dto.publisher.WithdrawalsOrderDto;
import com.waben.stock.interfaces.enums.PaymentState;
import com.waben.stock.interfaces.enums.PaymentType;
import com.waben.stock.interfaces.enums.WithdrawalsState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.util.UniqueCodeGenerator;

@Service
public class PaymentBusiness {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PaymentOrderService paymentOrderService;

	@Autowired
	private WithdrawalsOrderService withdrawalsOrderService;

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
		this.savePaymentOrder(paymentOrder);
		return result;
	}

	public void withdrawals(Long publisherId, BigDecimal amount, String name, String phone, String idCard,
			String bankCard, String bankCode) {
		// 请求提现
		String withdrawalsNo = UniqueCodeGenerator.generateWithdrawalsNo();
		CzWithholdResponse resp = CzWithholdOverSocket.withhold(withdrawalsNo, name, bankCard, phone, bankCode, amount);
		// 保存提现记录
		WithdrawalsOrderDto order = new WithdrawalsOrderDto();
		order.setAmount(amount);
		order.setBankCard(bankCard);
		order.setIdCard(idCard);
		order.setName(name);
		order.setPublisherId(publisherId);
		order.setThirdRespCode(resp.getRespCode());
		order.setThirdRespMsg(resp.getRespMsg());
		order.setWithdrawalsNo(withdrawalsNo);
		order.setState(resp.successful() ? WithdrawalsState.PROCESSING : WithdrawalsState.FAILURE);
		this.saveWithdrawalsOrder(order);
		// 提现异常
		if (!resp.successful()) {
			throw new ServiceException(ExceptionConstant.WITHDRAWALS_EXCEPTION, resp.getRespMsg());
		}
	}

	public PaymentOrderDto savePaymentOrder(PaymentOrderDto paymentOrder) {
		Response<PaymentOrderDto> orderResp = paymentOrderService.addPaymentOrder(paymentOrder);
		if ("200".equals(orderResp.getCode())) {
			return orderResp.getResult();
		}
		throw new ServiceException(orderResp.getCode());
	}

	public WithdrawalsOrderDto saveWithdrawalsOrder(WithdrawalsOrderDto withdrawalsOrderDto) {
		Response<WithdrawalsOrderDto> orderResp = withdrawalsOrderService.addWithdrawalsOrder(withdrawalsOrderDto);
		if ("200".equals(orderResp.getCode())) {
			return orderResp.getResult();
		}
		throw new ServiceException(orderResp.getCode());
	}
	
	public WithdrawalsOrderDto revisionWithdrawalsOrder(WithdrawalsOrderDto withdrawalsOrderDto) {
		Response<WithdrawalsOrderDto> orderResp = withdrawalsOrderService.modifyWithdrawalsOrder(withdrawalsOrderDto);
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

	public WithdrawalsOrderDto findByWithdrawalsNo(String withdrawalsNo) {
		Response<WithdrawalsOrderDto> orderResp = withdrawalsOrderService.fetchByWithdrawalsNo(withdrawalsNo);
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
			// 请求第三方支付
			CzPayResponse resp = CzPayOverHttp.payment(payReq.getPaymentNo(), payReq.getAmount(), payReq.getBankCode());
			if ("00".equals(resp.getRespCode())) {
				StringBuilder htmlBuiler = new StringBuilder();
				htmlBuiler.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>支付页面</title><script>");
				htmlBuiler.append("var url = \"" + resp.getHtml() + "\";window.location.href = url;");
				htmlBuiler.append("</script></head><body></body></html>");
				return htmlBuiler.toString();
			} else {
				logger.error("请求第三方支付异常：{}，{}", resp.getRespCode(), resp.getRespDesc());
				throw new ServiceException(ExceptionConstant.RECHARGE_EXCEPTION, resp.getRespDesc());
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
		String scriptContent = "<script>function call() {window.webkit.messageHandlers.callback.postMessage({paymentNo:'%s',result:'%s'});} call();</script>";
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

	public String czPaycallback(String data) {
		CzPayCallback callback = JacksonUtil.decode(data, CzPayCallback.class);
		String paymentNo = callback.getOrgSendSeqId();
		if ("00".equals(callback.getPayResult())) {
			// 支付成功
			payCallback(paymentNo, PaymentState.Paid);
			return "success";
		} else {
			return "fail";
		}
	}

	public String czPayReturn(String data) {
		StringBuilder result = new StringBuilder();
		result.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>回调页面</title></head><body>");
		String paymentNo = "";
		String stateStr = "";
		String scriptContent = "<script>function call() {if(window.appInterface) {window.appInterface.rechargeCallback('%s', '%s');} else {window.webkit.messageHandlers.callback.postMessage({paymentNo:'%s',result:'%s'});}} call();</script>";
		try {
			CzPayReturn returnObj = JacksonUtil.decode(data, CzPayReturn.class);
			paymentNo = returnObj.getSendSeqId();
			if ("00".equals(returnObj.getRespCode())) {
				stateStr = "支付成功";
			} else {
				stateStr = "支付异常";
			}
		} catch (Exception ex) {
			stateStr = "支付异常";
		}
		result.append(String.format(scriptContent, paymentNo, stateStr, paymentNo, stateStr));
		return result.toString();
	}

	public void czWithholdCallback(String withdrawalsNo, WithdrawalsState withdrawalsState, String thirdRespCode,
			String thirdRespMsg) {
		WithdrawalsOrderDto order = this.findByWithdrawalsNo(withdrawalsNo);
		order.setThirdRespCode(thirdRespCode);
		order.setThirdRespMsg(thirdRespMsg);
		this.revisionWithdrawalsOrder(order);
		if (order.getState() == WithdrawalsState.PROCESSING) {
			accountBusiness.withdrawals(order.getPublisherId(), withdrawalsNo, withdrawalsState);
		}
	}

}
