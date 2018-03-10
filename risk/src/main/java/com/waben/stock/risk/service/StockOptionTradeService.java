package com.waben.stock.risk.service;

import com.waben.stock.interfaces.service.stockoption.StockOptionTradeInterface;
import com.waben.stock.risk.service.fallback.StockOptionTradeServiceFallback;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "stockoption", path = "stockoptiontrade",fallback = StockOptionTradeServiceFallback.class,qualifier = "stockoptiontradeFeignService")
public interface StockOptionTradeService extends StockOptionTradeInterface {
}
