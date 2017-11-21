package com.waben.stock.applayer.tactics.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.applayer.tactics.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.stockcontent.StockInterface;

/**
 * 股票 reference服务接口
 * 
 * @author luomengan
 *
 */
@FeignClient(name = "stockcontent/stockcontent", path = "stock", configuration = FeignConfiguration.class)
@Primary
public interface StockService extends StockInterface {

}
