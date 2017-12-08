package com.waben.stock.applayer.operation.service.stock;

import com.waben.stock.applayer.operation.service.fallback.StockServiceFallback;
import com.waben.stock.applayer.operation.service.fallback.StrategyTypeServiceFallback;
import com.waben.stock.interfaces.service.stockcontent.StrategyTypeInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author Created by yuyidi on 2017/12/6.
 * @desc
 */
@FeignClient(name = "stockcontent/stockcontent", path = "strategytype",fallback = StrategyTypeServiceFallback.class,qualifier = "strategyTypeFeignService")
public interface StrategyTypeService extends StrategyTypeInterface {

}
