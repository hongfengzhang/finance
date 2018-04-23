package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.SettlementReferenceFallback;
import com.waben.stock.interfaces.service.buyrecord.SettlementInterface;

/**
 * 点买记录 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "buyrecord", path = "settlement", fallback = SettlementReferenceFallback.class, qualifier = "settlementReference")
public interface SettlementReference extends SettlementInterface {

}
