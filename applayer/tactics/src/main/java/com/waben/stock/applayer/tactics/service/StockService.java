package com.waben.stock.applayer.tactics.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.applayer.tactics.service.fallback.StockServiceFallback;
import com.waben.stock.applayer.tactics.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.StockInterface;

/**
 * 股票 reference服务接口
 * 
 * @author luomengan
 *
 */
@FeignClient(name = "stockcontent/stock", path = "stockcontent", fallback = StockServiceFallback.class, configuration = FeignConfiguration.class)
@Primary
public interface StockService extends StockInterface {

}
