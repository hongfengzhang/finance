package com.waben.stock.applayer.promotion.service.manage;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.service.fallback.RoleServiceFallback;
import com.waben.stock.interfaces.service.manage.RoleInterface;

@FeignClient(name = "manage", path = "role", fallback = RoleServiceFallback.class, qualifier = "roleReference")
public interface RoleService extends RoleInterface {

}
