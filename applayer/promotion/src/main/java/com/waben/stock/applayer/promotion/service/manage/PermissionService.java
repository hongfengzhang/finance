package com.waben.stock.applayer.promotion.service.manage;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.service.fallback.PermissionServiceFallback;
import com.waben.stock.interfaces.service.manage.PermissionInterface;

@FeignClient(name = "manage", path = "permission", fallback = PermissionServiceFallback.class, qualifier = "permissionReference")
public interface PermissionService extends PermissionInterface {

}
