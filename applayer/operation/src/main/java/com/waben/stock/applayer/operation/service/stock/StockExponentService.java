package com.waben.stock.applayer.operation.service.stock;

import com.waben.stock.applayer.operation.service.fallback.StockExponentServiceFallback;
import com.waben.stock.applayer.operation.service.fallback.StockServiceFallback;
import com.waben.stock.interfaces.service.stockcontent.StockExponentInterface;
import com.waben.stock.interfaces.service.stockcontent.StockInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author Created by yuyidi on 2017/11/22.
 * @desc
 */
@FeignClient(name = "stockcontent", path = "stockexponent",fallback = StockExponentServiceFallback.class,qualifier = "stockExponentFeignService")
public interface StockExponentService extends StockExponentInterface {

}
