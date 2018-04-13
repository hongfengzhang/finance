package com.waben.stock.applayer.tactics.reference;

import com.waben.stock.applayer.tactics.reference.fallback.ActivityPublisherReferenceFallback;
import com.waben.stock.interfaces.service.activity.ActivityPublisherInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "activity", path = "activitypublisher", fallback = ActivityPublisherReferenceFallback.class, qualifier =
        "activityPublisherFeignService")
public interface ActivityPublisherReference extends ActivityPublisherInterface {
}
