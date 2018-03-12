package com.waben.stock.applayer.promotion.reference.organization;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.reference.fallback.PromotionBuyRecordReferenceFallback;
import com.waben.stock.interfaces.service.organization.PromotionBuyRecordInterface;

/**
 * 推广渠道产生的策略 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "organization", path = "promotionBuyRecord", fallback = PromotionBuyRecordReferenceFallback.class, qualifier = "promotionBuyRecordReference")
public interface PromotionBuyRecordReference extends PromotionBuyRecordInterface {

}
