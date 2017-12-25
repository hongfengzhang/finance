package com.waben.stock.applayer.tactics.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.applayer.tactics.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.manage.CnapsInterface;

/**
 * Cnaps reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "manage", path = "cnaps", configuration = FeignConfiguration.class)
@Primary
public interface CnapsService extends CnapsInterface {

}
