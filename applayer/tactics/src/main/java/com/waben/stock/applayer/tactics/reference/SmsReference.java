package com.waben.stock.applayer.tactics.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.tactics.reference.fallback.SmsReferenceFallback;
import com.waben.stock.interfaces.service.message.SmsInterface;

/**
 * 短消息 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "message", path = "sms", fallback = SmsReferenceFallback.class, qualifier = "smsReference")
public interface SmsReference extends SmsInterface {

}
