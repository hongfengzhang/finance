package com.waben.stock.applayer.operation.service.activity;

import com.waben.stock.applayer.operation.service.fallback.PublisherTicketServiceFallback;
import com.waben.stock.interfaces.service.activity.PublisherTicketInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "activity", path = "publisherticket", fallback = PublisherTicketServiceFallback.class, qualifier =
        "publisherTicketFeignService")
public interface PublisherTicketService extends PublisherTicketInterface {
}
