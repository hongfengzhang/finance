package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.PublisherTeleChargeReferenceFallback;
import com.waben.stock.interfaces.service.activity.PublisherTeleChargeInterface;

@FeignClient(name = "activity", path = "publishertelecharge", fallback = PublisherTeleChargeReferenceFallback.class, qualifier =
        "publisherTeleChargeFeignService")
public interface PublisherTeleChargeReference extends PublisherTeleChargeInterface {
}
