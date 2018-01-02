package com.waben.stock.applayer.tactics.service;

import com.waben.stock.applayer.tactics.service.fallback.CircularsServiceFallback;
import com.waben.stock.applayer.tactics.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.manage.CircularsInterface;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

/**
 * 通告 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "manage", path = "circulars", fallback = CircularsServiceFallback.class, configuration =
        FeignConfiguration.class)
@Primary
public interface CircularsService extends CircularsInterface {

}
