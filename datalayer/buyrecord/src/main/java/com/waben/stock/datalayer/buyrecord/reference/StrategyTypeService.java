package com.waben.stock.datalayer.buyrecord.reference;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.datalayer.buyrecord.warpper.FeignConfiguration;
import com.waben.stock.interfaces.service.stockcontent.StrategyTypeInterface;

/**
 * 策略类型 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "stockcontent", path = "strategytype", configuration = FeignConfiguration.class)
@Primary
public interface StrategyTypeService extends StrategyTypeInterface {

}
