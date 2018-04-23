package com.waben.stock.applayer.admin.reference;

import com.waben.stock.applayer.admin.reference.fallback.RoleReferenceFallback;
import com.waben.stock.interfaces.service.manage.RoleInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
@FeignClient(name = "manage", path = "role", fallback = RoleReferenceFallback.class,qualifier = "roleFeignService")
public interface RoleReference extends RoleInterface {


}
