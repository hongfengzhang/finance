package com.waben.stock.applayer.tactics.reference;

import com.waben.stock.applayer.tactics.reference.fallback.PublisherDeduTicketReferenceFallback;
import com.waben.stock.interfaces.service.activity.PublisherDeduTicketInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "activity", path = "publisherdeduticket", fallback = PublisherDeduTicketReferenceFallback.class, qualifier =
        "publisherDeduTicketFeignService")
public interface PublisherDeduTicketReference extends PublisherDeduTicketInterface {
}
