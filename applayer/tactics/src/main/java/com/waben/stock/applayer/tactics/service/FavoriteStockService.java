package com.waben.stock.applayer.tactics.service;

import com.waben.stock.applayer.tactics.service.fallback.FavoriteStockServiceFallback;
import com.waben.stock.applayer.tactics.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.publisher.FavoriteStockInterface;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

/**
 * 收藏股票 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "publisher/publisher", path = "favoriteStock", fallback = FavoriteStockServiceFallback.class,
        configuration = FeignConfiguration.class)
@Primary
public interface FavoriteStockService extends FavoriteStockInterface {

}
