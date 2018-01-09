package com.waben.stock.applayer.strategist.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.strategist.reference.fallback.PublisherReferenceFallback;
import com.waben.stock.interfaces.service.publisher.PublisherInterface;

/**
 * 发布人 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "publisher", path = "publisher", fallback = PublisherReferenceFallback.class, qualifier = "publisherReference")
public interface PublisherReference extends PublisherInterface {
	
}
