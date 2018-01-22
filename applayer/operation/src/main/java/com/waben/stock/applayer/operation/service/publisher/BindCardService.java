package com.waben.stock.applayer.operation.service.publisher;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.operation.service.fallback.BindCardServiceFallback;
import com.waben.stock.interfaces.service.publisher.BindCardInterface;

@FeignClient(name = "publisher", path = "bindCard", fallback = BindCardServiceFallback.class, qualifier = "bindCardFeignService")
public interface BindCardService extends BindCardInterface {

}
