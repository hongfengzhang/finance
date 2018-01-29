package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.MenuBusiness;
import com.waben.stock.applayer.operation.util.SecurityAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Created by yuyidi on 2017/11/6.
 * @desc
 */
@SessionAttributes(value = {"menus","userName"})
@Controller
public class SystemController {

    @Autowired
    private MenuBusiness systemManageBusiness;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("userName", SecurityAccount.current().getUsername());
        model.addAttribute("menus",systemManageBusiness.menus());
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

