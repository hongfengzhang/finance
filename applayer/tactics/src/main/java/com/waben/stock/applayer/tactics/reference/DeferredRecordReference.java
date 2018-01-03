package com.waben.stock.applayer.tactics.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.tactics.reference.fallback.DeferredRecordReferenceFallback;
import com.waben.stock.interfaces.service.buyrecord.DeferredRecordInterface;

/**
 * 递延记录 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "buyrecord", path = "deferredrecord", fallback = DeferredRecordReferenceFallback.class, qualifier = "deferredRecordReference")
public interface DeferredRecordReference extends DeferredRecordInterface {

}
