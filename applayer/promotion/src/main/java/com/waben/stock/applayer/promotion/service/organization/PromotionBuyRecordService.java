package com.waben.stock.applayer.promotion.service.organization;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.service.fallback.PromotionBuyRecordServiceFallback;
import com.waben.stock.interfaces.service.organization.PromotionBuyRecordInterface;

/**
 * 推广渠道产生的策略 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "organization", path = "promotionBuyRecord", fallback = PromotionBuyRecordServiceFallback.class, qualifier = "promotionBuyRecordReference")
public interface PromotionBuyRecordService extends PromotionBuyRecordInterface {

}
