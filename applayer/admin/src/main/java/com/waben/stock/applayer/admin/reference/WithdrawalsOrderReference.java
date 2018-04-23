package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.WithdrawalsOrderReferenceFallback;
import com.waben.stock.interfaces.service.publisher.WithdrawalsOrderInterface;

/**
 * 支提现订单 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "publisher", path = "withdrawalsorder", fallback = WithdrawalsOrderReferenceFallback.class, qualifier = "withdrawalsOrderReference")
public interface WithdrawalsOrderReference extends WithdrawalsOrderInterface {

}
