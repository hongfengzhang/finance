package com.waben.stock.applayer.tactics.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.applayer.tactics.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.publisher.CapitalFlowInterface;

/**
 * 资金流水 reference服务接口
 * 
 * @author luomengan
 *
 */
@FeignClient(name = "publisher", path = "capitalFlow", configuration = FeignConfiguration.class)
@Primary
public interface CapitalFlowService extends CapitalFlowInterface {

}
