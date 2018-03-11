package com.waben.stock.applayer.promotion.reference.manage;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.reference.fallback.MenuReferenceFallback;
import com.waben.stock.interfaces.service.manage.MenuInterface;

@FeignClient(name = "manage", path = "menu", fallback = MenuReferenceFallback.class, qualifier = "menuReference")
public interface MenuReference extends MenuInterface {

}
