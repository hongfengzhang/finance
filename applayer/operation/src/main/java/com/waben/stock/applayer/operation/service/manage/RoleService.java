package com.waben.stock.applayer.operation.service.manage;

import com.waben.stock.applayer.operation.service.fallback.RoleServiceFallback;
import com.waben.stock.interfaces.service.manage.RoleInterface;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
@FeignClient(name = "manage/manage", path = "role", fallback = RoleServiceFallback.class)
@Primary
public interface RoleService extends RoleInterface {


}
