package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.AppVersionUpgradeReferenceFallback;
import com.waben.stock.interfaces.service.manage.AppVersionUpgradeInterface;

/**
 * app版本升级 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "manage", path = "appversionupgrade", fallback = AppVersionUpgradeReferenceFallback.class, qualifier = "appVersionUpgradeInterface")
public interface AppVersionUpgradeReference extends AppVersionUpgradeInterface {

}
