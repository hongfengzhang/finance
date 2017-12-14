package com.waben.stock.applayer.tactics.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.applayer.tactics.business.BindCardBusiness;
import com.waben.stock.applayer.tactics.business.PaymentBusiness;
import com.waben.stock.applayer.tactics.dto.payment.PayRequest;
import com.waben.stock.applayer.tactics.dto.payment.PayResponse;
import com.waben.stock.applayer.tactics.dto.payment.UnionPayRequest;
import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.enums.PaymentType;
import com.waben.stock.interfaces.pojo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 支付 Controller
 * 
 * @author luomengan
 *
 */
@RestController
@RequestMapping("/payment")
@Api(description = "支付")
public class PaymentController {

	@Autowired
	private PaymentBusiness paymentBusiness;

	@Autowired
	private BindCardBusiness bindCardBusiness;

	@GetMapping("/recharge")
	@ApiOperation(value = "充值", notes = "paymentType:1银联支付,2扫码支付")
	public void recharge(Long publisherId, Integer paymentType, BigDecimal amount, Long bindCardId,
			HttpServletResponse httpResp) {
		PaymentType payment = PaymentType.getByIndex(String.valueOf(paymentType));
		PayRequest payReq = null;
		if (payment == PaymentType.UnionPay) {
			BindCardDto bindCard = bindCardBusiness.findById(bindCardId);
			UnionPayRequest union = new UnionPayRequest();
			union.setAmount(amount);
			union.setBankCard(bindCard.getBankCard());
			union.setIdCard(bindCard.getIdCard());
			union.setName(bindCard.getName());
			union.setPhone(bindCard.getPhone());
			payReq = union;
		} else {
			throw new RuntimeException("not supported paymentType " + paymentType);
		}

		httpResp.setContentType("text/html;charset=UTF-8");
		String result = paymentBusiness.recharge(publisherId, payReq);
		try {
			PrintWriter writer = httpResp.getWriter();
			writer.write(result);
		} catch (IOException e) {
			throw new RuntimeException("http write interrupt");
		}
	}

	@GetMapping("/withdrawals")
	@ApiOperation(value = "提现", notes = "paymentType:1银联支付,2扫码支付")
	public Response<PayResponse> withdrawals(Integer paymentType, BigDecimal amount, Long bindCardId) {
		PaymentType payment = PaymentType.getByIndex(String.valueOf(paymentType));
		PayRequest payReq = null;
		if (payment == PaymentType.UnionPay) {
			BindCardDto bindCard = bindCardBusiness.findById(bindCardId);
			UnionPayRequest union = new UnionPayRequest();
			union.setAmount(amount);
			union.setBankCard(bindCard.getBankCard());
			union.setIdCard(bindCard.getIdCard());
			union.setName(bindCard.getName());
			union.setPhone(bindCard.getPhone());
			payReq = union;
		} else {
			throw new RuntimeException("not supported paymentType " + paymentType);
		}
		return new Response<>(
				paymentBusiness.withdrawals(SecurityUtil.getUserId(), SecurityUtil.getSerialCode(), payReq));
	}

	@GetMapping("/tbfpaycallback")
	@ApiOperation(value = "天下付支付后台回调")
	public void tbfPayCallback(HttpServletRequest request, HttpServletResponse httpResp)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("GBK");
		String cipherData = request.getParameter("cipher_data");
		// 处理回调
		String result = paymentBusiness.tbfPaycallback(cipherData);
		// 响应回调
		httpResp.setContentType("text/xml;charset=UTF-8");
		try {
			PrintWriter writer = httpResp.getWriter();
			writer.write(result);
		} catch (IOException e) {
			throw new RuntimeException("http write interrupt");
		}
	}

	@GetMapping("/tbfpayreturn")
	@ApiOperation(value = "天下付支付页面回调")
	public void tbfPayReturn(HttpServletRequest request, HttpServletResponse httpResp)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("GBK");
		String cipherData = request.getParameter("cipher_data");
		// 处理回调
		String result = paymentBusiness.tbfPayReturn(cipherData);
		// 响应回调
		httpResp.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter writer = httpResp.getWriter();
			writer.write(result);
		} catch (IOException e) {
			throw new RuntimeException("http write interrupt");
		}
	}

}
