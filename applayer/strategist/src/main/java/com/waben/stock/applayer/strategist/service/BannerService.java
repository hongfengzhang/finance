package com.waben.stock.applayer.strategist.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.applayer.strategist.service.fallback.BannerServiceFallback;
import com.waben.stock.applayer.strategist.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.manage.BannerInterface;

/**
 * 轮播 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "manage/manage", path = "banner", fallback = BannerServiceFallback.class, configuration =
        FeignConfiguration.class)
@Primary
public interface BannerService extends BannerInterface {

}
