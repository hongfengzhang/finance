package com.waben.stock.applayer.promotion.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.reference.fallback.PromotionStockOptionTradeReferenceFallback;
import com.waben.stock.interfaces.service.organization.PromotionStockOptionTradeInterface;

/**
 * 推广渠道产生的期权交易 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "organization", path = "promotionStockOptionTrade", fallback = PromotionStockOptionTradeReferenceFallback.class, qualifier = "promotionStockOptionTradeReference")
public interface PromotionStockOptionTradeReference extends PromotionStockOptionTradeInterface {

}
