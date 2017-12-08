package com.waben.stock.datalayer.buyrecord.reference;

import com.waben.stock.datalayer.buyrecord.reference.fallback.StockReferenceFallback;
import com.waben.stock.interfaces.service.stockcontent.StockInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author Created by yuyidi on 2017/12/8.
 * @desc
 */
@FeignClient(name = "stockcontent/stockcontent", path = "stock", fallback = StockReferenceFallback.class, qualifier =
        "stockFeignReference")
public interface StockReference extends StockInterface {
}
