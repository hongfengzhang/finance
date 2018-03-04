package com.waben.stock.datalayer.stockoption.reference;

import com.waben.stock.datalayer.stockoption.reference.fallback.PublisherReferenceFallback;
import com.waben.stock.interfaces.service.publisher.PublisherInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "publisher", path = "publisher", fallback = PublisherReferenceFallback.class, qualifier = "publisherFeignReference")
public interface PublisherReference extends PublisherInterface {
}
