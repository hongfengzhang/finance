package com.waben.stock.applayer.operation.service.buyrecord;

import com.waben.stock.applayer.operation.service.fallback.BuyRecordServiceFallback;
import com.waben.stock.interfaces.service.buyrecord.BuyRecordInterface;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author Created by yuyidi on 2017/12/2.
 * @desc
 */
@FeignClient(name = "buyrecord", path = "buyrecord", fallback = BuyRecordServiceFallback.class, qualifier =
        "buyRecordFeignService")
public interface BuyRecordService extends BuyRecordInterface {

}
