package com.waben.stock.applayer.tactics.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.tactics.reference.fallback.StockReferenceFallback;
import com.waben.stock.interfaces.service.stockcontent.StockInterface;

/**
 * 股票 reference服务接口
 * 
 * @author luomengan
 *
 */
@FeignClient(name = "stockcontent", path = "stock", fallback = StockReferenceFallback.class, qualifier = "stockReference")
public interface StockReference extends StockInterface {

}
