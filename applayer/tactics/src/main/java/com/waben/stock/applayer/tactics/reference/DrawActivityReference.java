package com.waben.stock.applayer.tactics.reference;

import com.waben.stock.applayer.tactics.reference.fallback.DrawActivityReferenceFallback;
import com.waben.stock.interfaces.service.activity.DrawActivityInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "activity", path = "drawactivity", fallback = DrawActivityReferenceFallback.class, qualifier =
        "drawActivityFeignService")
public interface DrawActivityReference extends DrawActivityInterface{

}
