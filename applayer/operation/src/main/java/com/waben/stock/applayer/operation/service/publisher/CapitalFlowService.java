package com.waben.stock.applayer.operation.service.publisher;

import com.waben.stock.applayer.operation.service.fallback.CapitalFlowServiceFallback;
import com.waben.stock.interfaces.service.publisher.CapitalFlowInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "publisher", path = "capitalFlow", fallback = CapitalFlowServiceFallback.class, qualifier = "capitalFlowFeignService")
public interface CapitalFlowService extends CapitalFlowInterface{
}
