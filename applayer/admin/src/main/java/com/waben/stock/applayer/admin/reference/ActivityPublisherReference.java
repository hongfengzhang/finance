package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.ActivityPublisherReferenceFallback;
import com.waben.stock.interfaces.service.activity.ActivityPublisherInterface;

@FeignClient(name = "activity", path = "activitypublisher", fallback = ActivityPublisherReferenceFallback.class, qualifier =
        "activityPublisherFeignService")
public interface ActivityPublisherReference extends ActivityPublisherInterface {
}
