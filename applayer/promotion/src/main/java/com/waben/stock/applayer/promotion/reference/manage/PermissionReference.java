package com.waben.stock.applayer.promotion.reference.manage;

import com.waben.stock.applayer.promotion.reference.fallback.OrganizationReferenceFallback;
import com.waben.stock.interfaces.service.manage.PermissionInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "manage", path = "permission", fallback = OrganizationReferenceFallback.class, qualifier = "permissionReference")
public interface PermissionReference extends PermissionInterface {

}
