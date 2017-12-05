package com.waben.stock.applayer.tactics.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.applayer.tactics.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.buyrecord.BuyRecordInterface;

/**
 * 点买记录 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "buyrecord/buyrecord", path = "buyrecord", configuration = FeignConfiguration.class)
@Primary
public interface BuyRecordService extends BuyRecordInterface {

}
