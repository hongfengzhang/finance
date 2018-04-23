package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.DrawActivityRadioReferenceFallback;
import com.waben.stock.interfaces.service.activity.DrawActivityRadioInterface;

@FeignClient(name = "activity", path = "drawactivityradio", fallback = DrawActivityRadioReferenceFallback.class, qualifier =
        "drawActivityRadioFeignService")
public interface DrawActivityRadioReference extends DrawActivityRadioInterface{
}
