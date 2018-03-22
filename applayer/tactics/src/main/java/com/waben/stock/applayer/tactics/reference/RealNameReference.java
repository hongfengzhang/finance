package com.waben.stock.applayer.tactics.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.tactics.reference.fallback.RealNameReferenceFallback;
import com.waben.stock.interfaces.service.publisher.RealNameInterface;

/**
 * 实名认证 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "publisher", path = "realname", fallback = RealNameReferenceFallback.class, qualifier = "realNameReference")
public interface RealNameReference extends RealNameInterface {

}
