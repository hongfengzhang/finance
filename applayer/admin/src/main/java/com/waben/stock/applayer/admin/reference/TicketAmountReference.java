package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.TicketAmountReferenceFallback;
import com.waben.stock.interfaces.service.activity.TicketMngInterface;

@FeignClient(name = "activity", path = "ticketamount", fallback = TicketAmountReferenceFallback.class, qualifier =
        "ticketAmountFeignService")
public interface TicketAmountReference extends TicketMngInterface {
}
