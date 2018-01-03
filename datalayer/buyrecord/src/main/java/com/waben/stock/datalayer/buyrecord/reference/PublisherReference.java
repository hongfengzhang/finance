package com.waben.stock.datalayer.buyrecord.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.datalayer.buyrecord.reference.fallback.PublisherReferenceFallback;
import com.waben.stock.interfaces.service.publisher.PublisherInterface;

@FeignClient(name = "publisher", path = "publisher", fallback = PublisherReferenceFallback.class, qualifier = "publisherFeignReference")
public interface PublisherReference extends PublisherInterface {
}
