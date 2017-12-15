package com.waben.stock.applayer.operation.service.stock;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.operation.service.fallback.StockServiceFallback;
import com.waben.stock.interfaces.service.stockcontent.StockInterface;

/**
 * @author Created by yuyidi on 2017/11/22.
 * @desc
 */
@FeignClient(name = "stockcontent/stockcontent", path = "stock",fallback = StockServiceFallback.class,qualifier = "stockFeignService")
public interface StockService extends StockInterface {

}
