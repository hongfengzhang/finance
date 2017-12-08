package com.waben.stock.applayer.operation.service.publisher;

import com.waben.stock.applayer.operation.service.fallback.CapitalAccountServiceFallback;
import com.waben.stock.interfaces.service.publisher.CapitalAccountInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "publisher/publisher", path = "capitalAccount", fallback = CapitalAccountServiceFallback.class, qualifier = "capitalAccountFeignService")
public interface CapitalAccountService extends CapitalAccountInterface {
}
