package com.waben.stock.applayer.promotion.reference.organization;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.reference.fallback.UserReferenceFallback;
import com.waben.stock.interfaces.service.organization.UserInterface;

/**
 * @author Created by yuyidi on 2018/3/12.
 * @desc
 */
@FeignClient(name = "organization", path = "user", fallback = UserReferenceFallback.class, qualifier = "userFeignService")
public interface UserReference extends UserInterface {

}
