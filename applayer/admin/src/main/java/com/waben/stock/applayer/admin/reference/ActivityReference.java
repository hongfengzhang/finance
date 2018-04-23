package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.ActivityReferenceFallback;
import com.waben.stock.interfaces.service.activity.ActivityMngInterface;

@FeignClient(name = "activity", path = "activity", fallback = ActivityReferenceFallback.class, qualifier =
        "activityFeignService")
public interface ActivityReference extends ActivityMngInterface{
}
