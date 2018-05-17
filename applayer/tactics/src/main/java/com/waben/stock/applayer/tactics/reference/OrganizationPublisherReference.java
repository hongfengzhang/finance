package com.waben.stock.applayer.tactics.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.waben.stock.applayer.tactics.reference.fallback.OrganizationPublisherReferenceFallback;
import com.waben.stock.interfaces.service.organization.OrganizationPublisherInterface;

/**
 * 机构推广的发布人 reference服务接口
 * 
 * @author luomengan
 *
 */
@FeignClient(name = "organization", path = "orgpublisher", fallback = OrganizationPublisherReferenceFallback.class, qualifier = "organizationPublisherReference")
public interface OrganizationPublisherReference extends OrganizationPublisherInterface {

}
