package com.waben.stock.applayer.promotion.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.promotion.service.organization.BenefitConfigService;
import com.waben.stock.applayer.promotion.reference.organization.BenefitConfigReference;
import com.waben.stock.interfaces.dto.organization.BenefitConfigDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.form.organization.BenefitConfigForm;

/**
 * 分成配置 Business
 * 
 * @author luomengan
 *
 */
@Service
public class BenefitConfigBusiness {

	@Autowired
	@Qualifier("benefitConfigReference")
	private BenefitConfigService reference;

	public List<BenefitConfigDto> benefitConfigList(Long orgId, Integer resourceType) {
		Response<List<BenefitConfigDto>> response = reference.benefitConfigList(orgId, resourceType);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public String strategyBenefitConfig(List<BenefitConfigForm> configFormList) {
		Response<String> response = reference.strategyBenefitConfig(configFormList);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public String stockoptionBenefitConfig(List<BenefitConfigForm> configFormList) {
		Response<String> response = reference.stockoptionBenefitConfig(configFormList);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
