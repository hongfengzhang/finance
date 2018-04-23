package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.CapitalAccountReferenceFallback;
import com.waben.stock.interfaces.service.publisher.CapitalAccountInterface;

/**
 * 资金账号 reference服务接口
 * 
 * @author luomengan
 *
 */
@FeignClient(name = "publisher", path = "capitalAccount", fallback = CapitalAccountReferenceFallback.class, qualifier = "capitalAccountReference")
public interface CapitalAccountReference extends CapitalAccountInterface {

}
