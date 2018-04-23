package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.StrategyTypeReferenceFallback;
import com.waben.stock.interfaces.service.stockcontent.StrategyTypeInterface;

/**
 * 策略类型 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "stockcontent", path = "strategytype", fallback = StrategyTypeReferenceFallback.class, qualifier = "strategyTypeReference")
public interface StrategyTypeReference extends StrategyTypeInterface {

}
