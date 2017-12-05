package com.waben.stock.applayer.operation.service.investor;

import com.waben.stock.applayer.operation.service.fallback.InvestorServiceFallback;
import com.waben.stock.applayer.operation.service.fallback.StaffServiceFallback;
import com.waben.stock.interfaces.service.inverstors.InvestorInterface;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@FeignClient(name = "investors/investors", path = "investor",fallback = InvestorServiceFallback.class)
public interface InvestorService extends InvestorInterface {

}
