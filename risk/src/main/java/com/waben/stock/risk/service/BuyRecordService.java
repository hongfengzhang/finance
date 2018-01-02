package com.waben.stock.risk.service;


import com.waben.stock.interfaces.service.buyrecord.BuyRecordInterface;
import com.waben.stock.risk.service.fallback.BuyRecordServiceFallback;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "buyrecord", path = "buyrecord", fallback = BuyRecordServiceFallback.class, qualifier =
		"buyRecordFeignService")
public interface BuyRecordService extends BuyRecordInterface{


}
