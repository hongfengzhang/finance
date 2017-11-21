package com.waben.stock.applayer.tactics.service;

import com.waben.stock.applayer.tactics.service.fallback.BindCardServiceFallback;
import com.waben.stock.applayer.tactics.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.publisher.BindCardInterface;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

/**
 * 绑卡 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "publisher/publisher", path = "bindCard", fallback = BindCardServiceFallback.class, configuration
        = FeignConfiguration.class)
@Primary
public interface BindCardService extends BindCardInterface {

}
