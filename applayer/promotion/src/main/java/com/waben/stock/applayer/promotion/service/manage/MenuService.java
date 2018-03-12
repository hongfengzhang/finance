package com.waben.stock.applayer.promotion.service.manage;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.service.fallback.MenuServiceFallback;
import com.waben.stock.interfaces.service.manage.MenuInterface;

@FeignClient(name = "manage", path = "menu", fallback = MenuServiceFallback.class, qualifier = "menuReference")
public interface MenuService extends MenuInterface {

}
