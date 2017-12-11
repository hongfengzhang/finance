package com.waben.stock.applayer.operation.service.stock;

import com.waben.stock.applayer.operation.service.fallback.LossServiceFallback;
import com.waben.stock.interfaces.service.stockcontent.LossInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "stockcontent/stockcontent", path = "loss",fallback = LossServiceFallback.class,qualifier = "lossFeignService")
public interface LossService extends LossInterface {

}
