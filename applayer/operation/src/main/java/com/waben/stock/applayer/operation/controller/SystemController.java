package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.MenuBusiness;
import com.waben.stock.applayer.operation.util.ExcelUtil;
import com.waben.stock.applayer.operation.util.SecurityAccount;
import com.waben.stock.applayer.operation.warpper.mail.MailService;
import com.waben.stock.applayer.operation.warpper.mail.QuotoInquiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Arrays;
import java.util.Date;

/**
 * @author Created by yuyidi on 2017/11/6.
 * @desc
 */
@SessionAttributes(value = {"menus", "userName"})
@Controller
public class SystemController {

    @Autowired
    private MenuBusiness systemManageBusiness;
    @Autowired
    private MailService mailService;

    @Value("${mail.contextPath}")
    private String contextPath;

    @GetMapping("/login")
    public String login() {
        QuotoInquiry quotoInquiry = new QuotoInquiry();
        quotoInquiry.setUnderlying("新湖瑞丰");
        quotoInquiry.setCode("600208");
        quotoInquiry.setStrike("100%");
        quotoInquiry.setAmount("100W");
        quotoInquiry.setPrice("7.55%");
        quotoInquiry.setTenor(6);
        quotoInquiry.setDate(new Date());
        String file = ExcelUtil.renderInquiry(contextPath, quotoInquiry);
        mailService.send("询价单", Arrays.asList(file), "lzh@youguwang.com.cn");
//        QuotoPurchase quotoPurchase = new QuotoPurchase();
//        quotoPurchase.setUnderlying("新湖瑞丰");
//        quotoPurchase.setCode("600208");
//        quotoPurchase.setStrike("100%");
//        quotoPurchase.setAmount("100W");
//        quotoPurchase.setBegin(new Date());
//        quotoPurchase.setEnd(new Date());
//        quotoPurchase.setRate("7.55%");
//        MailMessage mailMessage = new PurchaseMessage();
//        mailService.send("申购单", mailMessage.message(quotoPurchase), "lzh@youguwang.com.cn");
        return "login";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("userName", SecurityAccount.current().getUsername());
        model.addAttribute("menus", systemManageBusiness.menus());
        return "index";
    }

    @GetMapping("/403")
    public String forbidden() {
        return "403";
    }

    @GetMapping("/404")
    public String notfound() {
        return "404";
    }

    @GetMapping("/500")
    public String servererror() {
        return "500";
    }

    @GetMapping("/503")
    public String serviceerror() {
        return "503";
    }

    @GetMapping("/login-error")
    public String loginerror() {
        return "login";
    }


}

