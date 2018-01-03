package com.waben.stock.datalayer.buyrecord.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.datalayer.buyrecord.reference.fallback.StrategyTypeReferenceFallback;
import com.waben.stock.interfaces.service.stockcontent.StrategyTypeInterface;

/**
 * 策略类型 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "stockcontent", path = "strategytype", fallback = StrategyTypeReferenceFallback.class, qualifier = "strategyTypeFeignReference")
public interface StrategyTypeReference extends StrategyTypeInterface {

}
