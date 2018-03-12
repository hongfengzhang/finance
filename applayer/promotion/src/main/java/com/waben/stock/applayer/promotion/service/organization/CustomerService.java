package com.waben.stock.applayer.promotion.service.organization;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.service.fallback.CustomerServiceFallback;
import com.waben.stock.interfaces.service.organization.CustomerInterface;

/**
 * 客户 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "organization", path = "customer", fallback = CustomerServiceFallback.class, qualifier = "customerReference")
public interface CustomerService extends CustomerInterface {

}
