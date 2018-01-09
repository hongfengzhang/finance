package com.waben.stock.applayer.strategist.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.strategist.reference.fallback.CapitalFlowReferenceFallback;
import com.waben.stock.interfaces.service.publisher.CapitalFlowInterface;

/**
 * 资金流水 reference服务接口
 * 
 * @author luomengan
 *
 */
@FeignClient(name = "publisher", path = "capitalFlow", fallback = CapitalFlowReferenceFallback.class, qualifier = "capitalFlowReference")
public interface CapitalFlowReference extends CapitalFlowInterface {

}
