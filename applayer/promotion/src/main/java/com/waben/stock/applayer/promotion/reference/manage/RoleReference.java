package com.waben.stock.applayer.promotion.reference.manage;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.reference.fallback.RoleReferenceFallback;
import com.waben.stock.interfaces.service.manage.RoleInterface;

@FeignClient(name = "manage", path = "role", fallback = RoleReferenceFallback.class, qualifier = "roleReference")
public interface RoleReference extends RoleInterface {

}
