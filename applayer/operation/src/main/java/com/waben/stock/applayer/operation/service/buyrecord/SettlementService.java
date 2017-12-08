package com.waben.stock.applayer.operation.service.buyrecord;


import com.waben.stock.applayer.operation.service.fallback.SettlementFallback;
import com.waben.stock.interfaces.service.buyrecord.SettlementInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "buyrecord/buyrecord", path = "settlement", fallback = SettlementFallback.class, qualifier =
        "settlementFeignService")
public interface SettlementService extends SettlementInterface{
}
