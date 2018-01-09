package com.waben.stock.applayer.strategist.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.strategist.reference.fallback.CircularsReferenceFallback;
import com.waben.stock.interfaces.service.manage.CircularsInterface;

/**
 * 通告 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "manage", path = "circulars", fallback = CircularsReferenceFallback.class, qualifier = "circularsReference")
public interface CircularsReference extends CircularsInterface {

}
