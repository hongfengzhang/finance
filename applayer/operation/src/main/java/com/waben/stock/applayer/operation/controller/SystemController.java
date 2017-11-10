package com.waben.stock.applayer.operation.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by yuyidi on 2017/11/6.
 * @desc
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Value("${spring.jpa.show-sql:error}")
    private String flag;
    @Value("${spring.cloud.config.profile:error}")
    private String profile;


    @RequestMapping("/show")
    public String show() {
        return flag + ":" + profile;
    }
}
