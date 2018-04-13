package com.waben.stock.applayer.tactics.reference;

import com.waben.stock.applayer.tactics.reference.fallback.PublisherTeleChargeReferenceFallback;
import com.waben.stock.interfaces.service.activity.PublisherTeleChargeInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "activity", path = "publishertelecharge", fallback = PublisherTeleChargeReferenceFallback.class, qualifier =
        "publisherTeleChargeFeignService")
public interface PublisherTeleChargeReference extends PublisherTeleChargeInterface {
}
