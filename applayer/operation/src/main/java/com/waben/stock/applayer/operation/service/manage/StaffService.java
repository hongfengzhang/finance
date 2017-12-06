package com.waben.stock.applayer.operation.service.manage;

import com.waben.stock.applayer.operation.service.fallback.StaffServiceFallback;
import com.waben.stock.interfaces.service.manage.StaffInterface;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

/**
 * @author Created by yuyidi on 2017/11/15.
 * @desc
 */
@FeignClient(name = "manage/manage", path = "staff",fallback = StaffServiceFallback.class,qualifier = "staffFeignService")
public interface StaffService extends StaffInterface {

}
