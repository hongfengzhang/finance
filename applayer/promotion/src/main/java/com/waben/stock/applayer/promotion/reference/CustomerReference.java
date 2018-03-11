package com.waben.stock.applayer.promotion.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.reference.fallback.CustomerReferenceFallback;
import com.waben.stock.interfaces.service.organization.CustomerInterface;

/**
 * 客户 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "organization", path = "customer", fallback = CustomerReferenceFallback.class, qualifier = "customerReference")
public interface CustomerReference extends CustomerInterface {

}
