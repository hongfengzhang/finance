package com.waben.stock.applayer.operation.service.investor;

import com.waben.stock.applayer.operation.service.fallback.InvestorServiceFallback;
import com.waben.stock.applayer.operation.service.fallback.SecurityAccountServiceFallback;
import com.waben.stock.interfaces.service.inverstors.SecurityAccountInterface;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@FeignClient(name = "investors/investors", path = "securityaccount",fallback = SecurityAccountServiceFallback.class)
@Primary
public interface SecurityAccountService extends SecurityAccountInterface{


}
