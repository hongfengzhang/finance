package com.waben.stock.datalayer.investors.reference;

import com.waben.stock.datalayer.investors.reference.fallback.BuyRecordReferenceFallBack;
import com.waben.stock.interfaces.service.buyrecord.BuyRecordInterface;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

/**
 * @author Created by yuyidi on 2017/12/2.
 * @desc 投资人服务依赖点买服务
 */
@FeignClient(name = "buyrecord/buyrecord", path = "buyrecord", fallback = BuyRecordReferenceFallBack.class,qualifier = "buyRecordFeignReference")
public interface BuyRecordReference extends BuyRecordInterface {

}
