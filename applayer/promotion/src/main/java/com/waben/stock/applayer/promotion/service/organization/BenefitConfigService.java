package com.waben.stock.applayer.promotion.service.organization;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.service.fallback.BenefitConfigServiceFallback;
import com.waben.stock.interfaces.service.organization.BenefitConfigInterface;

/**
 * 分成配置 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "organization", path = "benefitConfig", fallback = BenefitConfigServiceFallback.class, qualifier = "benefitConfigReference")
public interface BenefitConfigService extends BenefitConfigInterface {

}
