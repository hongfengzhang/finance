package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.CnapsReferenceFallback;
import com.waben.stock.interfaces.service.manage.CnapsInterface;

/**
 * Cnaps reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "manage", path = "cnaps", fallback = CnapsReferenceFallback.class, qualifier = "cnapsReference")
public interface CnapsReference extends CnapsInterface {

}
