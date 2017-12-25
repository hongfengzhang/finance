package com.waben.stock.applayer.strategist.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.applayer.strategist.service.fallback.BindCardServiceFallback;
import com.waben.stock.applayer.strategist.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.publisher.BindCardInterface;

/**
 * 绑卡 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "publisher", path = "bindCard", fallback = BindCardServiceFallback.class, configuration
        = FeignConfiguration.class)
@Primary
public interface BindCardService extends BindCardInterface {

}
