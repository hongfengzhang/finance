package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.BannerReferenceFallback;
import com.waben.stock.interfaces.service.manage.BannerInterface;

/**
 * 轮播 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "manage", path = "banner", fallback = BannerReferenceFallback.class, qualifier = "bannerReference")
public interface BannerReference extends BannerInterface {

}
