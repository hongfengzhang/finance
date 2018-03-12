package com.waben.stock.applayer.promotion.service.organization;

import com.waben.stock.applayer.promotion.service.fallback.OrganizationServiceFlowFallback;
import com.waben.stock.interfaces.service.organization.OrganizationAccountFlowInterface;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "orgAccountFlow", path = "orgAccountFlow", fallback = OrganizationServiceFlowFallback.class, qualifier = "organizationAccountFlowReference")
public interface OrganizationAccountFlowService extends OrganizationAccountFlowInterface{
}
