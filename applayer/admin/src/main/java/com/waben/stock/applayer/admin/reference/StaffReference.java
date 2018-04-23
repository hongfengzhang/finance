package com.waben.stock.applayer.admin.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.admin.reference.fallback.StaffReferenceFallback;
import com.waben.stock.interfaces.service.manage.StaffInterface;

@FeignClient(name = "manage", path = "staff", fallback = StaffReferenceFallback.class, qualifier = "staffReference")
public interface StaffReference extends StaffInterface {

}
