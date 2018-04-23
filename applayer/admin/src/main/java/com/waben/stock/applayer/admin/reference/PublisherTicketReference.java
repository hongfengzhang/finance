package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.PublisherTicketReferenceFallback;
import com.waben.stock.interfaces.service.activity.PublisherTicketInterface;

@FeignClient(name = "activity", path = "publisherticket", fallback = PublisherTicketReferenceFallback.class, qualifier =
        "publisherTicketFeignService")
public interface PublisherTicketReference extends PublisherTicketInterface {
}
