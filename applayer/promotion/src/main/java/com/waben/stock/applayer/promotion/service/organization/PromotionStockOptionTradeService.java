package com.waben.stock.applayer.promotion.service.organization;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.service.fallback.PromotionStockOptionTradeServiceFallback;
import com.waben.stock.interfaces.service.organization.PromotionStockOptionTradeInterface;

/**
 * 推广渠道产生的期权交易 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "organization", path = "promotionStockOptionTrade", fallback = PromotionStockOptionTradeServiceFallback.class, qualifier = "promotionStockOptionTradeReference")
public interface PromotionStockOptionTradeService extends PromotionStockOptionTradeInterface {

}
