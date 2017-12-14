package com.waben.stock.risk.service;

import com.waben.stock.interfaces.service.stockcontent.StockInterface;
import com.waben.stock.risk.service.fallback.StockServiceFallback;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author Created by yuyidi on 2017/12/8.
 * @desc
 */
@FeignClient(name = "stockcontent/stockcontent", path = "stock", fallback = StockServiceFallback.class, qualifier =
        "stockFeignService")
public interface StockService extends StockInterface {
}
