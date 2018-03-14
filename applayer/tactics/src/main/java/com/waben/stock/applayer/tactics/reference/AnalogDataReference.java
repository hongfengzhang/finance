package com.waben.stock.applayer.tactics.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.tactics.reference.fallback.AnalogDataReferenceFallback;
import com.waben.stock.interfaces.service.manage.AnalogDataInterface;

/**
 * 模拟数据 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "manage", path = "analogdata", fallback = AnalogDataReferenceFallback.class, qualifier = "analogDataReference")
public interface AnalogDataReference extends AnalogDataInterface {

}
