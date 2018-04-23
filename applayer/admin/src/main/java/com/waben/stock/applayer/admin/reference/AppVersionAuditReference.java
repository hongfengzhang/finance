package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.AppVersionReferenceFallback;
import com.waben.stock.interfaces.service.manage.AppVersionAuditInterface;

/**
 * app版本 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "manage", path = "appversionaudit", fallback = AppVersionReferenceFallback.class, qualifier = "appVersionReference")
public interface AppVersionAuditReference extends AppVersionAuditInterface {

}
