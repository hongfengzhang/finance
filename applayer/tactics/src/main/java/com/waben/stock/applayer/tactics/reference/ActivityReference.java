package com.waben.stock.applayer.tactics.reference;

import com.waben.stock.applayer.tactics.reference.fallback.ActivityReferenceFallback;
import com.waben.stock.interfaces.service.activity.ActivityMngInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "activity", path = "activity", fallback = ActivityReferenceFallback.class, qualifier =
        "activityFeignService")
public interface ActivityReference extends ActivityMngInterface{
}
