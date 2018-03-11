package com.waben.stock.applayer.promotion.reference;

import com.waben.stock.applayer.promotion.reference.fallback.OrganizationAccountFlowFallback;
import com.waben.stock.interfaces.service.organization.OrganizationAccountFlowInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "orgAccountFlow", path = "orgAccountFlow", fallback = OrganizationAccountFlowFallback.class, qualifier = "organizationAccountFlowReference")
public interface OrganizationAccountFlowReference extends OrganizationAccountFlowInterface{
}
