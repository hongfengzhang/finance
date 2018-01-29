package com.waben.stock.applayer.operation.service.message;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.operation.service.fallback.MessagingServiceFallback;
import com.waben.stock.applayer.operation.warpper.FeignConfiguration;
import com.waben.stock.interfaces.service.message.MessagingInterface;

/**
 * 
 * @author Created by hujian on 2018年1月4日
 */
@FeignClient(name = "message", path = "messaging", fallback = MessagingServiceFallback.class, qualifier = "messagingFeignService", configuration = FeignConfiguration.class)
public interface MessagingService extends MessagingInterface {

}
