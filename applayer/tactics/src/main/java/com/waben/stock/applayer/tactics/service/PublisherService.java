package com.waben.stock.applayer.tactics.service;

import com.waben.stock.applayer.tactics.service.fallback.PublisherServiceFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Created by yuyidi on 2017/11/3.
 * @desc
 */
@FeignClient(name = "PUBLISHER/publisher", path = "publish", fallback = PublisherServiceFallback.class)
@Primary
public interface PublisherService {

    @RequestMapping("/echo")
    String echo();


}
