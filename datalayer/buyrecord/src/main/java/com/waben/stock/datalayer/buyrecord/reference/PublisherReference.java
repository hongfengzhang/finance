package com.waben.stock.datalayer.buyrecord.reference;

import com.waben.stock.datalayer.buyrecord.reference.fallback.PublisherReferenceFallback;
import com.waben.stock.interfaces.service.publisher.PublisherInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "publisher/publisher", path = "publisher", fallback = PublisherReferenceFallback.class
        , qualifier = "publisherFeignReference")
public interface PublisherReference extends PublisherInterface {
}
