package com.waben.stock.applayer.operation.service.manage;

import com.waben.stock.applayer.operation.service.fallback.BannerServiceFallback;
import com.waben.stock.interfaces.service.manage.BannerInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "manage/manage", path = "banner", fallback = BannerServiceFallback.class, qualifier =
        "bannerFeignService")
public interface BannerService extends BannerInterface {

}
