package com.waben.stock.applayer.operation.service.manage;

import com.waben.stock.applayer.operation.service.fallback.PermissionServiceFallback;
import com.waben.stock.interfaces.service.manage.PermissionInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author Created by yuyidi on 2017/12/11.
 * @desc
 */
@FeignClient(name = "manage", path = "permission", fallback = PermissionServiceFallback.class, qualifier =
        "permissionFeignService")
public interface PermissionService extends PermissionInterface {

}
