package com.waben.stock.applayer.operation.service.manage;

import com.waben.stock.applayer.operation.service.fallback.RoleServiceFallback;
import com.waben.stock.interfaces.service.manage.RoleInterface;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
@FeignClient(name = "manage", path = "role", fallback = RoleServiceFallback.class,qualifier = "roleFeignService")
public interface RoleService extends RoleInterface {


}
