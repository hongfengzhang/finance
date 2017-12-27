package com.waben.stock.applayer.strategist.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.applayer.strategist.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.buyrecord.DeferredRecordInterface;

/**
 * 递延记录 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "buyrecord", path = "deferredrecord", configuration = FeignConfiguration.class)
@Primary
public interface DeferredRecordService extends DeferredRecordInterface {

}
