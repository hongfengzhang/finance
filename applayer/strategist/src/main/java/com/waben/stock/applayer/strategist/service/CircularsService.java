package com.waben.stock.applayer.strategist.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.applayer.strategist.service.fallback.CircularsServiceFallback;
import com.waben.stock.applayer.strategist.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.manage.CircularsInterface;

/**
 * 通告 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "manage/manage", path = "circulars", fallback = CircularsServiceFallback.class, configuration =
        FeignConfiguration.class)
@Primary
public interface CircularsService extends CircularsInterface {

}
