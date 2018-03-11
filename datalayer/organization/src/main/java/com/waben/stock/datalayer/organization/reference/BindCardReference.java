package com.waben.stock.datalayer.organization.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.datalayer.organization.reference.fallback.BindCardReferenceFallback;
import com.waben.stock.interfaces.service.publisher.BindCardInterface;

/**
 * 绑卡 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "publisher", path = "bindCard", fallback = BindCardReferenceFallback.class, qualifier = "bindCardReference")
public interface BindCardReference extends BindCardInterface {

}
