package com.waben.stock.applayer.strategist.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.strategist.reference.fallback.FavoriteStockReferenceFallback;
import com.waben.stock.interfaces.service.publisher.FavoriteStockInterface;

/**
 * 收藏股票 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "publisher", path = "favoriteStock", fallback = FavoriteStockReferenceFallback.class, qualifier = "favoriteStockReference")
public interface FavoriteStockReference extends FavoriteStockInterface {

}
