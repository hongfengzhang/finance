package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.SystemManageBusiness;
import com.waben.stock.applayer.operation.exception.AuthMethodNotSupportedException;
import com.waben.stock.applayer.operation.service.manage.MenuService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.MenuDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/6.
 * @desc
 */
@Controller
public class SystemController {

    @Autowired
    private SystemManageBusiness systemManageBusiness;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String root() {
        return "index";
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
//        throw new AuthMethodNotSupportedException(ExceptionConstant.SECURITY_METHOD_UNSUPPORT_EXCEPTION);
        return "login";
    }

}

