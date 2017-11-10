package com.waben.stock.applayer.tactics.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by yuyidi on 2017/11/7.
 * @desc
 */
@RestController
@RequestMapping("/system")
@RefreshScope
public class SystemController {

    @Value("${spring.jpa.show-sql:error}")
    private String show;

    @GetMapping("/index")
    public String index() {
        return show;
    }
}
