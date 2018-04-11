package com.waben.stock.applayer.operation.service.activity;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.operation.service.fallback.ActivityMngServiceFallback;
import com.waben.stock.interfaces.service.activity.ActivityMngInterface;

/**
 * 
 * 
 * @author guowei 2018/4/11
 *
 */

@FeignClient(name = "activity", path = "activity", fallback = ActivityMngServiceFallback.class, qualifier =
        "activityFeignService")
public interface ActivityMngService extends ActivityMngInterface {
	
}
