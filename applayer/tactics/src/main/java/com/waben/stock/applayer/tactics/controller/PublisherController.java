package com.waben.stock.applayer.tactics.controller;

import com.waben.stock.applayer.tactics.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by yuyidi on 2017/11/3.
 * @desc
 */
@RestController
@RequestMapping("/publisher")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @RequestMapping("/echo")
    public String echo() {
        return publisherService.echo();
    }

}
