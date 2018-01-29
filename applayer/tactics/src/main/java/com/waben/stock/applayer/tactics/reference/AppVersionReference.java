package com.waben.stock.applayer.tactics.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.tactics.reference.fallback.AppVersionReferenceFallback;
import com.waben.stock.interfaces.service.manage.AppVersionInterface;

/**
 * app版本 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "manage", path = "appversion", fallback = AppVersionReferenceFallback.class, qualifier = "appVersionReference")
public interface AppVersionReference extends AppVersionInterface {

}
