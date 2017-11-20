package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.MenuBusiness;
import com.waben.stock.interfaces.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Created by yuyidi on 2017/11/6.
 * @desc
 */
@Controller
public class SystemController {

    @RequestMapping("/conform")
    public String conform(){
        return "conform";
    }

    @RequestMapping("/left")
    public String left(){
        return "aa/left";
    }

    @RequestMapping("/right")
    public String right(){
        return "aa/right";
    }
    @Autowired
    private MenuBusiness systemManageBusiness;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String root() {
        return "aa";
    }

    @GetMapping("/index")
    public String index() {
        System.out.println(JacksonUtil.encode(systemManageBusiness.menus()));
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

    @GetMapping("/login-error")
    public String loginerror() {
        return "login";
    }

}

