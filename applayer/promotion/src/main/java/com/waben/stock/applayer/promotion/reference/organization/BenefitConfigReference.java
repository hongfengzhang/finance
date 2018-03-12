package com.waben.stock.applayer.promotion.reference.organization;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.reference.fallback.BenefitConfigReferenceFallback;
import com.waben.stock.interfaces.service.organization.BenefitConfigInterface;

/**
 * 分成配置 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "organization", path = "benefitConfig", fallback = BenefitConfigReferenceFallback.class, qualifier = "benefitConfigReference")
public interface BenefitConfigReference extends BenefitConfigInterface {

}
