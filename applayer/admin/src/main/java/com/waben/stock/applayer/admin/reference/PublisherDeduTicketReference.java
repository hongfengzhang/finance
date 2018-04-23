package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.PublisherDeduTicketReferenceFallback;
import com.waben.stock.interfaces.service.activity.PublisherDeduTicketInterface;

@FeignClient(name = "activity", path = "publisherdeduticket", fallback = PublisherDeduTicketReferenceFallback.class, qualifier =
        "publisherDeduTicketFeignService")
public interface PublisherDeduTicketReference extends PublisherDeduTicketInterface {
}
