package com.waben.stock.applayer.tactics.reference;

import com.waben.stock.applayer.tactics.reference.fallback.DrawActivityRadioReferenceFallback;
import com.waben.stock.interfaces.service.activity.DrawActivityRadioInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "activity", path = "drawactivityradio", fallback = DrawActivityRadioReferenceFallback.class, qualifier =
        "drawActivityRadioFeignService")
public interface DrawActivityRadioReference extends DrawActivityRadioInterface{
}
