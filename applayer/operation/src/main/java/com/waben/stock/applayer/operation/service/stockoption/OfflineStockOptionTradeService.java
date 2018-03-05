package com.waben.stock.applayer.operation.service.stockoption;

import com.waben.stock.applayer.operation.service.fallback.OfflineStockOptionTradesServiceFallback;
import com.waben.stock.interfaces.service.stockoption.OfflineStockOptionTradeInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "stockoption", path = "offlinestockoptiontrade",fallback = OfflineStockOptionTradesServiceFallback.class,qualifier = "offlinestockoptiontradeFeignService")
public interface OfflineStockOptionTradeService extends OfflineStockOptionTradeInterface {
}
