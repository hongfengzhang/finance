package com.waben.stock.applayer.tactics.reference;

import com.waben.stock.applayer.tactics.reference.fallback.PublisherTicketReferenceFallback;
import com.waben.stock.interfaces.service.activity.PublisherTicketInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "activity", path = "publisherticket", fallback = PublisherTicketReferenceFallback.class, qualifier =
        "publisherTicketFeignService")
public interface PublisherTicketReference extends PublisherTicketInterface {
}
