package com.waben.stock.applayer.promotion.reference.organization;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.promotion.reference.fallback.OrganizationAccountFlowReferenceFallback;
import com.waben.stock.interfaces.service.organization.OrganizationAccountFlowInterface;

@FeignClient(name = "organization", path = "organizationAccountFlow", fallback = OrganizationAccountFlowReferenceFallback.class, qualifier = "organizationAccountFlowReference")
public interface OrganizationAccountFlowReference extends OrganizationAccountFlowInterface{
}
