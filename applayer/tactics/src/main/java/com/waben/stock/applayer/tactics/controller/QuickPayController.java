package com.waben.stock.applayer.tactics.controller;

import com.alibaba.fastjson.JSONObject;
import com.waben.stock.applayer.tactics.business.*;
import com.waben.stock.applayer.tactics.payapi.czpay.config.CzBankType;
import com.waben.stock.applayer.tactics.payapi.paypal.config.PayPalConfig;
import com.waben.stock.applayer.tactics.payapi.paypal.config.RSAUtil;
import com.waben.stock.applayer.tactics.security.SecurityUtil;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.enums.BankType;
import com.waben.stock.interfaces.enums.WithdrawalsState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.PasswordCrypt;

import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.jdbc.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.waben.stock.applayer.tactics.business.QuickPayBusiness;


@Controller
@RequestMapping("/quickpay")
public class QuickPayController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuickPayBusiness quickPayBusiness;

    @Autowired
    private PublisherBusiness publisherBusiness;

    @Autowired
    private BindCardBusiness bindCardBusiness;

    @Autowired
    private CapitalAccountBusiness capitalAccountBusiness;

    @Autowired
    private PaymentBusiness paymentBusiness;

    @GetMapping("/sdquickpay")
    @ApiOperation(value = "杉德快捷支付")
    public String quickPay1(Model model, @RequestParam(required = true) BigDecimal amount,
                            @RequestParam(required = true) Long phone) {
        Map<String, String> map = quickPayBusiness.quickpay(amount, phone.toString());
        model.addAttribute("result", map);
        return "shandepay/payment";
    }


    @PostMapping("/sdpaycallback")
    @ApiOperation(value = "杉德支付后台回调")
    public void sdPayCallback(HttpServletRequest request, HttpServletResponse httpResp)
            throws UnsupportedEncodingException {
        // 处理回调
        String result = quickPayBusiness.sdPaycallback(request);
        // 响应回调
        httpResp.setContentType("text/xml;charset=UTF-8");
        try {
            PrintWriter writer = httpResp.getWriter();
            writer.write(result);
        } catch (IOException e) {
            throw new RuntimeException("http write interrupt");
        }
    }

    @GetMapping("/sdpayreturn")
    @ApiOperation(value = "杉德页面回调")
    public void sdPayReturn(HttpServletResponse httpResp) throws UnsupportedEncodingException {
        // 处理回调
        String result = quickPayBusiness.sdPayReturn();
        // 响应回调
        httpResp.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter writer = httpResp.getWriter();
            writer.write(result);
        } catch (IOException e) {
            throw new RuntimeException("http write interrupt");
        }
    }


    @PostMapping("/sdpaycsa")
    @ApiOperation(value = "杉德支付提现")
    @ResponseBody
    public Response<String> sdwithdrawals(@RequestParam(required = true) BigDecimal amount,
                                          @RequestParam(required = true) Long bindCardId, @RequestParam(required = true) String paymentPassword) {
        // 判断是否为测试用户，测试用户不能提现
        PublisherDto publisher = publisherBusiness.findById(SecurityUtil.getUserId());
        if (publisher.getIsTest() != null && publisher.getIsTest()) {
            throw new ServiceException(ExceptionConstant.TESTUSER_NOWITHDRAWALS_EXCEPTION);
        }
        // 验证支付密码
        CapitalAccountDto capitalAccount = capitalAccountBusiness.findByPublisherId(SecurityUtil.getUserId());
        String storePaymentPassword = capitalAccount.getPaymentPassword();
        if (storePaymentPassword == null || "".equals(storePaymentPassword)) {
            throw new ServiceException(ExceptionConstant.PAYMENTPASSWORD_NOTSET_EXCEPTION);
        }
        if (!PasswordCrypt.match(paymentPassword, storePaymentPassword)) {
            throw new ServiceException(ExceptionConstant.PAYMENTPASSWORD_WRONG_EXCEPTION);
        }
        // 检查余额
        if (amount.compareTo(capitalAccount.getAvailableBalance()) > 0) {
            throw new ServiceException(ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION);
        }
        Response<String> resp = new Response<String>();
        BindCardDto bindCard = bindCardBusiness.findById(bindCardId);
        CzBankType bankType = CzBankType.getByPlateformBankType(BankType.getByBank(bindCard.getBankName()));
        if (bankType == null) {
            throw new ServiceException(ExceptionConstant.BANKCARD_NOTSUPPORT_EXCEPTION);
        }
        logger.info("验证通过,提现开始");
        quickPayBusiness.withdrawals(SecurityUtil.getUserId(), amount, bindCard.getName(), bindCard.getPhone(),
                bindCard.getIdCard(), bindCard.getBankCard(), bankType.getCode(), bindCard.getBranchName());
        resp.setResult("success");
        return resp;
    }


    @GetMapping("/qqh5")
    @ApiOperation(value = "彩拓QQh5")
    @ResponseBody
    public Response<Map> qqh5(@RequestParam(required = true) BigDecimal amount,
                              @RequestParam(required = true) Long phone) {
        Response<Map> result = quickPayBusiness.ctQQh5(amount, phone.toString());

        return result;
    }

    @GetMapping("/jdh5")
    @ApiOperation(value = "彩拓京东h5")
    @ResponseBody
    public Response<Map> jdh5(@RequestParam(required = true) BigDecimal amount,
                              @RequestParam(required = true) Long phone) {
        Response<Map> result = quickPayBusiness.jdh5(amount, phone.toString());
        return result;
    }


    @GetMapping("/qqcallback")
    @ApiOperation(value = "彩拓QQ后台回调")
    @ResponseBody
    public String qqPayCallback(HttpServletRequest request, HttpServletResponse httpResp)
            throws UnsupportedEncodingException {
        // 处理回调
        String result = quickPayBusiness.qqPaycallback(request);
        // 响应回调
        logger.info("回调响应的结果是:{}", result);
        return result;
    }

    @GetMapping("/jdcallback")
    @ApiOperation(value = "彩拓京东后台回调")
    @ResponseBody
    public String jdPayCallback(HttpServletRequest request, HttpServletResponse httpResp)
            throws UnsupportedEncodingException {
        // 处理回调
        String result = quickPayBusiness.jdPaycallback(request);
        // 响应回调
        logger.info("回调响应的结果是:{}", result);
        return result;
    }

    @GetMapping("/qqpayreturn")
    @ApiOperation(value = "彩拓QQ支付页面回调")
    public void qqPayReturn(HttpServletResponse httpResp) throws UnsupportedEncodingException {
        // 处理回调
        String result = quickPayBusiness.sdPayReturn();
        // 响应回调
        httpResp.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter writer = httpResp.getWriter();
            writer.write(result);
        } catch (IOException e) {
            throw new RuntimeException("http write interrupt");
        }
    }

    @GetMapping("/jdpayreturn")
    @ApiOperation(value = "彩拓京东页面回调")
    public void jdPayReturn(HttpServletResponse httpResp) throws UnsupportedEncodingException {
        // 处理回调
        String result = quickPayBusiness.sdPayReturn();
        // 响应回调
        httpResp.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter writer = httpResp.getWriter();
            writer.write(result);
        } catch (IOException e) {
            throw new RuntimeException("http write interrupt");
        }
    }


    @RequestMapping("/paypal")
    @ApiOperation(value = "连连快捷支付")
    @ResponseBody
    public Response<Map> paypal(@RequestParam(required = true) Long bindCardId, @RequestParam(required = true) BigDecimal amount) {

        Response<Map> result = quickPayBusiness.payPal(amount, bindCardId, SecurityUtil.getUserId());
        return result;
    }

    @RequestMapping("/paypalcallback")
    @ApiOperation(value = "连连快捷支付后台回调")
    @ResponseBody
    public JSONObject payPalPayCallback(HttpServletRequest request) throws UnsupportedEncodingException {
        // 处理回调
        JSONObject result = quickPayBusiness.payPalcallback(request);
        // 响应回调
        return result;
    }

    @RequestMapping("/paypalreturn")
    @ApiOperation(value = "连连快捷支付页面回调")
    public void payPalPayReturn(HttpServletResponse httpResp, HttpServletRequest request) throws UnsupportedEncodingException {
        // 处理回调
        String result = quickPayBusiness.payPalreturn(request);
        // 响应回调
        httpResp.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter writer = httpResp.getWriter();
            writer.write(result);
        } catch (IOException e) {
            throw new RuntimeException("http write interrupt");
        }
    }

    @RequestMapping("/paypalcsa")
    @ApiOperation(value = "连连支付代付接口")
    @ResponseBody
    public Response<String> paypalcsa(@RequestParam(required = true) BigDecimal amount,
                                      @RequestParam(required = true) Long bindCardId, @RequestParam(required = true) String paymentPassword) {
        // 判断是否为测试用户，测试用户不能提现
        PublisherDto publisher = publisherBusiness.findById(SecurityUtil.getUserId());
        if (publisher.getIsTest() != null && publisher.getIsTest()) {
            throw new ServiceException(ExceptionConstant.TESTUSER_NOWITHDRAWALS_EXCEPTION);
        }
        // 验证支付密码
        CapitalAccountDto capitalAccount = capitalAccountBusiness.findByPublisherId(SecurityUtil.getUserId());
        String storePaymentPassword = capitalAccount.getPaymentPassword();
        if (storePaymentPassword == null || "".equals(storePaymentPassword)) {
            throw new ServiceException(ExceptionConstant.PAYMENTPASSWORD_NOTSET_EXCEPTION);
        }
        if (!PasswordCrypt.match(paymentPassword, storePaymentPassword)) {
            throw new ServiceException(ExceptionConstant.PAYMENTPASSWORD_WRONG_EXCEPTION);
        }
        // 检查余额
        if (amount.compareTo(capitalAccount.getAvailableBalance()) > 0) {
            throw new ServiceException(ExceptionConstant.AVAILABLE_BALANCE_NOTENOUGH_EXCEPTION);
        }
        Response<String> resp = new Response<String>();
        BindCardDto bindCard = bindCardBusiness.findById(bindCardId);
        CzBankType bankType = CzBankType.getByPlateformBankType(BankType.getByBank(bindCard.getBankName()));
        if (bankType == null) {
            throw new ServiceException(ExceptionConstant.BANKCARD_NOTSUPPORT_EXCEPTION);
        }
        logger.info("验证通过,提现开始");
        quickPayBusiness.payPalCSA(SecurityUtil.getUserId(), amount, bindCard.getName(), bindCard.getPhone(),
                bindCard.getIdCard(), bindCard.getBankCard(), bankType.getCode(), bindCard.getBranchName());
        resp.setResult("success");
        return resp;
    }


    @RequestMapping("/paypalnotify")
    @ApiOperation(value = "连连支付代付回调接口")
    @ResponseBody
    public JSONObject payPalWithholdCallback(HttpServletRequest request) throws IOException {
        logger.info("异步调用");
        String result = "";
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = request.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        logger.info("代付异步通知内容为:{}", jsonObject.toJSONString());
        String no_order = jsonObject.getString("no_order");//流水号
        String result_pay = jsonObject.getString("result_pay");//付款结果
        String settle_date = jsonObject.getString("settle_date");//付款日期
        JSONObject returnObject = new JSONObject();
        // TODO 签名校验
        String sign = jsonObject.getString("sign");
        String oid_str = quickPayBusiness.genSignData(jsonObject);
        //签名验证
        Boolean checksign = RSAUtil.checksign(PayPalConfig.public_key, oid_str, sign);
        if (!checksign) {
            returnObject.put("ret_code", "1002");
            returnObject.put("ret_msg", "验证签名失败");
            return returnObject;
        }
        logger.info("连连代付异步通知签名验证成功");
        // 处理回调result_pay settle_date
        logger.info("receive paypalpay withhold notify result: {},{}", jsonObject.get("no_order"), jsonObject.get("result_pay"));
        if (jsonObject.get("no_order") != null) {
            quickPayBusiness.payPalWithholdCallback(no_order,
                    "SUCCESS".equals(result_pay) ? WithdrawalsState.PROCESSED : WithdrawalsState.FAILURE, settle_date,
                    result_pay);
            // 响应回调
            returnObject.put("ret_code", "0000");
            returnObject.put("ret_msg", "交易成功");
        } else {
            returnObject.put("ret_code", "1001");
            returnObject.put("ret_msg", "交易失败");
        }
        return returnObject;
    }


    @RequestMapping("/quickbank")
    @ApiOperation(value = "网贝收银台支付调接口")
    @ResponseBody
    public Response<Map> quickBank(@RequestParam(required = true) BigDecimal amount) {
        Response<Map> result = quickPayBusiness.wabenPay(amount, SecurityUtil.getUserId());
        return result;
    }

    @RequestMapping("/platform")
    @ApiOperation(value = "网贝收银台支付调接口")
    @ResponseBody
    public Response<Map> platform(@RequestParam(required = true) BigDecimal amount) {
        Response<Map> result = quickPayBusiness.wabenPay(amount, SecurityUtil.getUserId());
        return result;
    }



    @RequestMapping("/wbreturn")
    @ApiOperation(value = "网贝收银台同步回调接口")
    @ResponseBody
    public void callback(HttpServletResponse httpResp) throws UnsupportedEncodingException {
        // 处理回调
        String result = quickPayBusiness.sdPayReturn();
        // 响应回调
        httpResp.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter writer = httpResp.getWriter();
            writer.write(result);
        } catch (IOException e) {
            throw new RuntimeException("http write interrupt");
        }
    }

    @RequestMapping("/wbcallback")
    @ApiOperation(value = "网贝收银台异步回调接口")
    @ResponseBody
    public String notify(HttpServletRequest request) {
        String result = quickPayBusiness.wbCallback(request);
        return result;
    }


}
