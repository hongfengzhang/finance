package com.waben.stock.applayer.operation.service.activity;

import com.waben.stock.applayer.operation.service.fallback.ActivityServiceFallback;
import com.waben.stock.interfaces.service.activity.ActivityMngInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "activity", path = "activity", fallback = ActivityServiceFallback.class, qualifier =
        "activityFeignService")
public interface ActivityService extends ActivityMngInterface {
}
