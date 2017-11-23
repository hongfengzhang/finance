package com.waben.stock.applayer.tactics.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.applayer.tactics.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.publisher.CapitalAccountInterface;

/**
 * 
 * @author luomengan
 *
 */
@FeignClient(name = "publisher/publisher", path = "capitalAccount", configuration = FeignConfiguration.class)
@Primary
public interface CapitalAccountService extends CapitalAccountInterface {

}
