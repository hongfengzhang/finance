package com.waben.stock.applayer.strategist.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.strategist.reference.fallback.AreaInfoReferenceFallback;
import com.waben.stock.interfaces.service.manage.AreaInfoInterface;

/**
 * 区域 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "manage", path = "areainfo", fallback = AreaInfoReferenceFallback.class, qualifier = "areaInfoReference")
public interface AreaInfoReference extends AreaInfoInterface {

}
