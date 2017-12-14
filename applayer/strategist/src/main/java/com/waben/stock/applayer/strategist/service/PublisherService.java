package com.waben.stock.applayer.strategist.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.applayer.strategist.service.fallback.PublisherServiceFallback;
import com.waben.stock.applayer.strategist.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.publisher.PublisherInterface;

/**
 * @author Created by yuyidi on 2017/11/3.
 * @desc
 */
@FeignClient(name = "publisher/publisher", path = "publisher", fallback = PublisherServiceFallback.class,
        configuration = FeignConfiguration.class)
@Primary
public interface PublisherService extends PublisherInterface {
	
}
