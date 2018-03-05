package com.waben.stock.applayer.operation.service.stockoption;

import com.waben.stock.applayer.operation.service.fallback.StockOptionTradeServiceFallback;
import com.waben.stock.interfaces.service.stockoption.StockOptionTradeInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "stockoption", path = "stockoptiontrade",fallback = StockOptionTradeServiceFallback.class,qualifier = "stockoptiontradeFeignService")
public interface StockOptionTradeService extends StockOptionTradeInterface {
}
