package com.waben.stock.applayer.operation.service.stockoption;

import com.waben.stock.applayer.operation.service.fallback.InquiryResultServiceFallback;
import com.waben.stock.interfaces.service.stockoption.InquiryResultInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "stockoption", path = "inquiryresult",fallback = InquiryResultServiceFallback.class,qualifier = "inquiryresultFeignService")
public interface InquiryResultService extends InquiryResultInterface {
}
