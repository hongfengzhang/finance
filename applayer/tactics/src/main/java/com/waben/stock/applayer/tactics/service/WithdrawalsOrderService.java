package com.waben.stock.applayer.tactics.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.applayer.tactics.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.publisher.WithdrawalsOrderInterface;

/**
 * 支提现订单 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "publisher/publisher", path = "withdrawalsorder", configuration = FeignConfiguration.class)
@Primary
public interface WithdrawalsOrderService extends WithdrawalsOrderInterface {

}
