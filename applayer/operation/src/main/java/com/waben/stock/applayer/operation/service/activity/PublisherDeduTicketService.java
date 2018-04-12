package com.waben.stock.applayer.operation.service.activity;

import com.waben.stock.applayer.operation.service.fallback.PublisherDeduTicketServiceFallback;
import com.waben.stock.interfaces.service.activity.PublisherDeduTicketInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "activity", path = "publisherdeduticket", fallback = PublisherDeduTicketServiceFallback.class, qualifier =
        "publisherDeduTicketFeignService")
public interface PublisherDeduTicketService extends PublisherDeduTicketInterface {
}
