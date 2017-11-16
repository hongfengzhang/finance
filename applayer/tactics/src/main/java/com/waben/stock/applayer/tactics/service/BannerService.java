package com.waben.stock.applayer.tactics.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.applayer.tactics.service.fallback.BannerServiceFallback;
import com.waben.stock.applayer.tactics.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.BannerInterface;

/**
 * 轮播 reference服务接口
 * 
 * @author luomengan
 *
 */
@FeignClient(name = "manage/banner", path = "manage", fallback = BannerServiceFallback.class, configuration = FeignConfiguration.class)
@Primary
public interface BannerService extends BannerInterface {

}
