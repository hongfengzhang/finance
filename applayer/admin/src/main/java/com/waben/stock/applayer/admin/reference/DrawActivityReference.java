package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.DrawActivityReferenceFallback;
import com.waben.stock.interfaces.service.activity.DrawActivityInterface;

@FeignClient(name = "activity", path = "drawactivity", fallback = DrawActivityReferenceFallback.class, qualifier =
        "drawActivityFeignService")
public interface DrawActivityReference extends DrawActivityInterface{

}
