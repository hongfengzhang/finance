package com.waben.stock.applayer.operation.service.stockoption;

import com.waben.stock.applayer.operation.service.fallback.MailUrlInfoServiceFallback;
import com.waben.stock.interfaces.service.stockoption.MailUrlInfoInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created by Administrator on 2018/3/17.
 */
@FeignClient(name = "stockoption", path = "mailUrlInfo",fallback = MailUrlInfoServiceFallback.class,qualifier = "mailUrlInfoFeignService")
public interface MailUrlInfoService extends MailUrlInfoInterface {
}
