package com.waben.stock.applayer.operation.service.stockoption;

import com.waben.stock.applayer.operation.service.fallback.StockOptionOrgServiceFallback;
import com.waben.stock.interfaces.service.stockoption.StockOptionOrgInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "stockoption", path = "stockoptionorg",fallback = StockOptionOrgServiceFallback.class,qualifier = "stockoptionorgFeignService")
public interface StockOptionOrgService extends StockOptionOrgInterface {
}
