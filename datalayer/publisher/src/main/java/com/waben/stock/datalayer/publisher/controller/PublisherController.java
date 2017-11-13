package com.waben.stock.datalayer.publisher.controller;

import com.waben.stock.datalayer.publisher.service.PublisherService;
import com.waben.stock.interfaces.dto.PublisherDto;
import com.waben.stock.interfaces.service.PublisherInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by yuyidi on 2017/11/5.
 * @desc
 */
@RestController
@RequestMapping("/publisher")
public class PublisherController implements PublisherInterface {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PublisherService publisherService;

    public PublisherDto findById(@PathVariable Long id) {
        logger.info("获取发布策略人信息:{}", id);
        return publisherService.findById(id);
    }
}
