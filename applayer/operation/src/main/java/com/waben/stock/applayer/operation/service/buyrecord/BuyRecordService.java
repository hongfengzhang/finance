package com.waben.stock.applayer.operation.service.buyrecord;

import com.waben.stock.applayer.operation.service.fallback.BuyRecordServiceFallback;
import com.waben.stock.interfaces.service.buyrecord.BuyRecordInterface;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

/**
 * @author Created by yuyidi on 2017/12/2.
 * @desc
 */
@FeignClient(name = "buyrecord/buyrecord", path = "buyrecord", fallback = BuyRecordServiceFallback.class)
@Primary
public interface BuyRecordService extends BuyRecordInterface {

}
