package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.StockOptionTradeReferenceFallback;
import com.waben.stock.interfaces.service.stockoption.StockOptionTradeInterface;

/**
 * 期权交易 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "stockoption", path = "stockoptiontrade", fallback = StockOptionTradeReferenceFallback.class, qualifier = "stockOptionTradeReference")
public interface StockOptionTradeReference extends StockOptionTradeInterface {

}
