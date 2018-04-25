package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.StockOptionOrgReferenceFallback;
import com.waben.stock.interfaces.service.stockoption.StockOptionOrgInterface;

/**
 * 期权第三方机构 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "stockoption", path = "stockoptionorg", fallback = StockOptionOrgReferenceFallback.class, qualifier = "stockOptionOrgReference")
public interface StockOptionOrgReference extends StockOptionOrgInterface {

}
