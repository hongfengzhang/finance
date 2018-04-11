package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.BuyRecordBusiness;
import com.waben.stock.applayer.operation.business.MenuBusiness;
import com.waben.stock.applayer.operation.business.StockOptionTradeBusiness;
import com.waben.stock.applayer.operation.util.SecurityAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Created by yuyidi on 2017/11/6.
 * @desc
 */
@SessionAttributes(value = {"menus", "userName","companyName"})
@Controller
public class SystemController {

    @Autowired
    private MenuBusiness systemManageBusiness;

    @Autowired
    private BuyRecordBusiness buyRecordBusiness;

    @Autowired
    private StockOptionTradeBusiness stockOptionTradeBusiness;

    @Value("${mail.contextPath}")
    private String contextPath;
    @Value("${company.name}")
    private String compayName;

    @GetMapping("/login")
    public String login(ModelMap modelMap) {
        modelMap.put("companyName",compayName);
        return "login";
    }

    @GetMapping("/index")
    public String index(ModelMap model) {
        Map<String,Object> buyRecordProfitAndPosition = buyRecordBusiness.fetchBuyRecordProfitAndPosition();
        Map<String,Object>stockOptionTradeProfitAndPosition = stockOptionTradeBusiness.fetchStockOptionTradeProfitAndPosition();
        model.addAttribute("buyRecordProfitAndPosition", buyRecordProfitAndPosition);
        model.addAttribute("stockOptionTradeProfitAndPosition", stockOptionTradeProfitAndPosition);
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

