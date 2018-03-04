package com.waben.stock.datalayer.stockoption.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.datalayer.stockoption.reference.fallback.CapitalAccountReferenceFallback;
import com.waben.stock.interfaces.service.publisher.CapitalAccountInterface;

/**
 * 资金账号 reference服务接口
 * 
 * @author luomengan
 *
 */
@FeignClient(name = "publisher", path = "capitalAccount", fallback = CapitalAccountReferenceFallback.class, qualifier = "capitalAccountFeignReference")
public interface CapitalAccountReference extends CapitalAccountInterface {

}
