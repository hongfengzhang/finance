package com.waben.stock.applayer.operation.service.publisher;

import com.waben.stock.applayer.operation.service.fallback.PublisherServiceFallback;
import com.waben.stock.interfaces.service.publisher.PublisherInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "publisher", path = "publisher", fallback = PublisherServiceFallback.class, qualifier = "publisherFeignService")
public interface PublisherService extends PublisherInterface {

}
