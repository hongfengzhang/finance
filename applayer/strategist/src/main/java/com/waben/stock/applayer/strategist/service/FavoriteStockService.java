package com.waben.stock.applayer.strategist.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.applayer.strategist.service.fallback.FavoriteStockServiceFallback;
import com.waben.stock.applayer.strategist.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.publisher.FavoriteStockInterface;

/**
 * 收藏股票 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "publisher", path = "favoriteStock", fallback = FavoriteStockServiceFallback.class,
        configuration = FeignConfiguration.class)
@Primary
public interface FavoriteStockService extends FavoriteStockInterface {

}
