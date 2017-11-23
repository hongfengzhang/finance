package com.waben.stock.applayer.operation.service.stock;

import com.waben.stock.applayer.operation.service.fallback.StaffServiceFallback;
import com.waben.stock.applayer.operation.service.fallback.StockServiceFallback;
import com.waben.stock.interfaces.service.stockcontent.StockInterface;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

/**
 * @author Created by yuyidi on 2017/11/22.
 * @desc
 */
@FeignClient(name = "stockcontent/stockcontent", path = "stock",fallback = StockServiceFallback.class)
@Primary
public interface StockService extends StockInterface {

}
