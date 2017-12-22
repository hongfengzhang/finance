package com.waben.stock.applayer.operation.service.manage;

import com.waben.stock.applayer.operation.service.fallback.CircularsServiceFallback;
import com.waben.stock.interfaces.service.manage.CircularsInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author Created by yuyidi on 2017/12/11.
 * @desc
 */
@FeignClient(name = "manage", path = "circulars", fallback = CircularsServiceFallback.class, qualifier =
        "circularsFeignService")
public interface CircularsService extends CircularsInterface {

}
