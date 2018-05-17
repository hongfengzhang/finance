package com.waben.stock.applayer.strategist.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.strategist.reference.fallback.StockOptionCycleReferenceFallback;
import com.waben.stock.interfaces.service.stockoption.StockOptionCycleInterface;

/**
 * 期权周期 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "stockoption", path = "stockoptioncycle", fallback = StockOptionCycleReferenceFallback.class, qualifier = "stockOptionCycleReference")
public interface StockOptionCycleReference extends StockOptionCycleInterface {

	
	
}
