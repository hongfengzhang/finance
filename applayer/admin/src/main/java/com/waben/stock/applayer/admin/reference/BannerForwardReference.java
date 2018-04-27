package com.waben.stock.applayer.admin.reference;

import com.waben.stock.applayer.admin.reference.fallback.BannerForwardReferenceFallback;
import com.waben.stock.interfaces.service.manage.BannerForwardInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * 轮播 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "manage", path = "bannerforward", fallback = BannerForwardReferenceFallback.class, qualifier = "bannerForwardReference")
public interface BannerForwardReference extends BannerForwardInterface {

}
