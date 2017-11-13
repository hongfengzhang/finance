package com.waben.stock.applayer.tactics.controller;

import com.waben.stock.applayer.tactics.service.PublisherService;
import com.waben.stock.interfaces.dto.PublisherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by yuyidi on 2017/11/3.
 * @desc
 */
@RestController
@RequestMapping("/publish")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping("/{id}")
    public PublisherDto echo(@PathVariable Long id) {
        return publisherService.findById(id);
    }

}
