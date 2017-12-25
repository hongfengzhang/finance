package com.waben.stock.applayer.tactics.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.applayer.tactics.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.publisher.PaymentOrderInterface;

/**
 * 支付订单 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "publisher", path = "paymentorder", configuration = FeignConfiguration.class)
@Primary
public interface PaymentOrderService extends PaymentOrderInterface {

}
