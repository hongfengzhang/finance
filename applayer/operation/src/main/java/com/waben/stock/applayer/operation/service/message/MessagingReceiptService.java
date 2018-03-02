package com.waben.stock.applayer.operation.service.message;

import com.waben.stock.applayer.operation.service.fallback.MessagingReceiptServiceFallback;
import com.waben.stock.applayer.operation.service.fallback.MessagingServiceFallback;
import com.waben.stock.applayer.operation.warpper.FeignConfiguration;
import com.waben.stock.interfaces.dto.message.MessageReceiptDto;
import com.waben.stock.interfaces.service.message.MessageReceiptInterface;
import com.waben.stock.interfaces.service.message.MessagingInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * 
 * @author Created by hujian on 2018年1月4日
 */
@FeignClient(name = "message", path = "messagingReceipt", fallback = MessagingReceiptServiceFallback.class, qualifier = "messagingReceiptFeignService", configuration = FeignConfiguration.class)
public interface MessagingReceiptService extends MessageReceiptInterface {

}
