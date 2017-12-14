package com.waben.stock.applayer.operation.service.stock;

import com.waben.stock.applayer.operation.service.fallback.AmountValueServiceFallback;
import com.waben.stock.interfaces.service.stockcontent.AmountValueInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "stockcontent/stockcontent", path = "amountvalue",fallback = AmountValueServiceFallback.class,qualifier = "amountvalueFeignService")
public interface AmountValueService extends AmountValueInterface {
}
