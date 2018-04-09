package com.waben.stock.applayer.promotion.reference.organization;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.reference.fallback.PriceMarkupConfigReferenceFallback;
import com.waben.stock.interfaces.service.organization.PriceMarkupConfigInterface;

/**
 * 加价配置 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "organization", path = "priceMarkupConfig", fallback = PriceMarkupConfigReferenceFallback.class, qualifier = "priceMarkupConfigReference")
public interface PriceMarkupConfigReference extends PriceMarkupConfigInterface {

}
