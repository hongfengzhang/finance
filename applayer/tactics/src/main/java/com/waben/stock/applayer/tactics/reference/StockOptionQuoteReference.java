package com.waben.stock.applayer.tactics.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.tactics.reference.fallback.StockOptionQuoteReferenceFallback;
import com.waben.stock.interfaces.service.stockoption.StockOptionQuoteInterface;

/**
 * 期权报价 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "stockoption", path = "stockoptionquote", fallback = StockOptionQuoteReferenceFallback.class, qualifier = "stockOptionQuoteReference")
public interface StockOptionQuoteReference extends StockOptionQuoteInterface {

	
	
}
