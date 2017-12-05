package com.waben.stock.applayer.operation.service.manage;

import com.waben.stock.applayer.operation.service.fallback.MenuServiceFallback;
import com.waben.stock.applayer.operation.service.fallback.StaffServiceFallback;
import com.waben.stock.interfaces.service.manage.MenuInterface;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

/**
 * @author Created by yuyidi on 2017/11/17.
 * @desc
 */
@FeignClient(name = "manage/manage", path = "menu",fallback = MenuServiceFallback.class)
public interface MenuService extends MenuInterface {


}
