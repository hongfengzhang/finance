package com.waben.stock.applayer.promotion.reference.manage;

import com.waben.stock.applayer.promotion.reference.fallback.OrganizationReferenceFallback;
import com.waben.stock.interfaces.service.manage.RoleInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "manage", path = "role", fallback = OrganizationReferenceFallback.class, qualifier = "roleReference")
public interface RoleReference extends RoleInterface {

}
