package com.waben.stock.applayer.promotion.service.organization;

import com.waben.stock.applayer.promotion.service.fallback.UserServiceFallback;
import com.waben.stock.interfaces.service.organization.UserInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author Created by yuyidi on 2018/3/12.
 * @desc
 */
@FeignClient(name = "organization", path = "user", fallback = UserServiceFallback.class, qualifier = "userFeignService")
public interface UserService extends UserInterface {

}
