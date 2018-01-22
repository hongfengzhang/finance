package com.waben.stock.applayer.operation.service.publisher;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.operation.service.fallback.PaymentOrderServiceFallback;
import com.waben.stock.interfaces.service.publisher.PaymentOrderInterface;

@FeignClient(name = "publisher" , path = "paymentOrder" , fallback = PaymentOrderServiceFallback.class , qualifier = "paymentOrderFeignService")
public interface PaymentOrderService extends PaymentOrderInterface {

}
