package com.waben.stock.applayer.operation.service.activity;

import com.waben.stock.applayer.operation.service.fallback.TicketAmountServiceFallback;
import com.waben.stock.interfaces.service.activity.TicketMngInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "activity", path = "ticketamount", fallback = TicketAmountServiceFallback.class, qualifier =
        "ticketAmountFeignService")
public interface TicketAmountService extends TicketMngInterface {
}
