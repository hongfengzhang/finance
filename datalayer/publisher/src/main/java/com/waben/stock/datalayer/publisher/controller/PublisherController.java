package com.waben.stock.datalayer.publisher.controller;

import com.netflix.appinfo.EurekaInstanceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by yuyidi on 2017/11/5.
 * @desc
 */
@RestController
@RequestMapping("/publish")
public class PublisherController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EurekaInstanceConfig instanceConfig;

    @RequestMapping("/echo")
    public String echo() {
        logger.info("实例Id:{},Host{}", instanceConfig.getInstanceId(), instanceConfig.getHostName(false));
        return instanceConfig.getInstanceId();
    }
}
