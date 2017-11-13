package com.waben.stock.applayer.tactics.service;

import com.waben.stock.applayer.tactics.service.fallback.PublisherServiceFallback;
import com.waben.stock.interfaces.dto.PublisherDto;
import com.waben.stock.interfaces.service.PublisherInterface;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Created by yuyidi on 2017/11/3.
 * @desc
 */
@FeignClient(name = "publisher/publisher", path = "publisher",fallback = PublisherServiceFallback.class)
@Primary
public interface PublisherService extends PublisherInterface{

}
