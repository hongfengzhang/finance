package com.waben.stock.datalayer.buyrecord.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.datalayer.buyrecord.reference.fallback.InvestorReferenceFallback;
import com.waben.stock.interfaces.service.inverstors.InvestorInterface;

/**
 * 投资人 reference服务接口
 * 
 * @author luomengan
 *
 */
@FeignClient(name = "investors", path = "investor", fallback = InvestorReferenceFallback.class, qualifier = "investorReference")
public interface InvestorReference extends InvestorInterface {

}
