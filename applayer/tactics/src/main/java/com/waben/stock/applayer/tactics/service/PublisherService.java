package com.waben.stock.applayer.tactics.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;

import com.waben.stock.applayer.tactics.service.fallback.PublisherServiceFallback;
import com.waben.stock.applayer.tactics.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.pojo.Response;
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
