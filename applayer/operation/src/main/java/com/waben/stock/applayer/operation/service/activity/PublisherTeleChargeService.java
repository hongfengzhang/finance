package com.waben.stock.applayer.operation.service.activity;

import com.waben.stock.applayer.operation.service.fallback.PublisherTeleChargeServiceFallback;
import com.waben.stock.interfaces.service.activity.PublisherTeleChargeInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "activity", path = "publishertelecharge", fallback = PublisherTeleChargeServiceFallback.class, qualifier =
        "publisherTeleChargeFeignService")
public interface PublisherTeleChargeService extends PublisherTeleChargeInterface {
}
