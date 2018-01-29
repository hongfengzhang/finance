package com.waben.stock.applayer.tactics.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.tactics.reference.fallback.MessagingReferenceFallback;
import com.waben.stock.interfaces.service.message.MessagingInterface;

/**
 * 消息 reference服务接口
 * 
 * @author luomengan
 *
 */
@FeignClient(name = "message", path = "messaging", fallback = MessagingReferenceFallback.class, qualifier = "messagingReference")
public interface MessagingReference extends MessagingInterface {

}
