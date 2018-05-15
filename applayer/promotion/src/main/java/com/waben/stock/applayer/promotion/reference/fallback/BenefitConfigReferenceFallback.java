package com.waben.stock.applayer.promotion.reference.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.promotion.reference.organization.BenefitConfigReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.BenefitConfigDto;
import com.waben.stock.interfaces.dto.organization.SettlementMethodDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.form.organization.BenefitConfigForm;

/**
 * 分成配置 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class BenefitConfigReferenceFallback implements BenefitConfigReference {

	@Override
	public Response<List<BenefitConfigDto>> benefitConfigList(Long orgId, Integer resourceType) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<String> strategyBenefitConfig(List<BenefitConfigForm> configFormList) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<String> stockoptionBenefitConfig(List<BenefitConfigForm> configFormList) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<SettlementMethodDto> getSettlement() {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
