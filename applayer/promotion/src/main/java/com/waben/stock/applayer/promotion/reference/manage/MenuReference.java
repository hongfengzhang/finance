package com.waben.stock.applayer.promotion.reference.manage;

import com.waben.stock.applayer.promotion.reference.fallback.OrganizationReferenceFallback;
import com.waben.stock.interfaces.service.manage.MenuInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "manage", path = "menu", fallback = OrganizationReferenceFallback.class, qualifier = "menuReference")
public interface MenuReference extends MenuInterface {

}
