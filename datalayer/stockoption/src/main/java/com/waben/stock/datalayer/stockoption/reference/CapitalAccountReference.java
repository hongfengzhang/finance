package com.waben.stock.datalayer.stockoption.reference;

import com.waben.stock.datalayer.stockoption.reference.fallback.CapitalAccountReferenceFallback;
import com.waben.stock.interfaces.service.publisher.CapitalAccountInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * 资金账号 reference服务接口
 * 
 * @author luomengan
 *
 */
@FeignClient(name = "publisher", path = "capitalAccount", fallback = CapitalAccountReferenceFallback.class, qualifier = "capitalAccountFeignReference")
public interface CapitalAccountReference extends CapitalAccountInterface {

}
