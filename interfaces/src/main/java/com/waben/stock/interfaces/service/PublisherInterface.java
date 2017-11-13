package com.waben.stock.interfaces.service;

import com.waben.stock.interfaces.dto.PublisherDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Created by yuyidi on 2017/11/12.
 * @desc
 */
public interface PublisherInterface {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    PublisherDto findById(@PathVariable("id") Long id);
}
