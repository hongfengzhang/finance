package com.waben.stock.applayer.tactics.reference;

import com.waben.stock.applayer.tactics.reference.fallback.TicketAmountReferenceFallback;
import com.waben.stock.interfaces.service.activity.TicketMngInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "activity", path = "ticketamount", fallback = TicketAmountReferenceFallback.class, qualifier =
        "ticketAmountFeignService")
public interface TicketAmountReference extends TicketMngInterface {
}
