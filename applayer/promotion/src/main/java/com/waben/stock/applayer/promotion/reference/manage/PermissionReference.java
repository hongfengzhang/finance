package com.waben.stock.applayer.promotion.reference.manage;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.reference.fallback.PermissionReferenceFallback;
import com.waben.stock.interfaces.service.manage.PermissionInterface;

@FeignClient(name = "manage", path = "permission", fallback = PermissionReferenceFallback.class, qualifier = "permissionReference")
public interface PermissionReference extends PermissionInterface {

}
