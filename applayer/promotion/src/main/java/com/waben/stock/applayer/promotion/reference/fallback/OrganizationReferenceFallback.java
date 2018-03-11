package com.waben.stock.applayer.promotion.reference.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.promotion.reference.OrganizationReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.OrganizationDetailDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.dto.organization.TreeNode;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.form.organization.OrganizationForm;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationQuery;

/**
 * 机构 reference服务接口fallback
 *
 * @author luomengan
 */
@Component
public class OrganizationReferenceFallback implements OrganizationReference {

	@Override
	public Response<OrganizationDto> addition(OrganizationForm orgForm) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PageInfo<OrganizationDto>> adminPage(OrganizationQuery query) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public List<TreeNode> adminTree(Long orgId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<List<OrganizationDto>> listByParentId(Long parentId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<OrganizationDetailDto> detail(Long orgId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<OrganizationDto> modifyName(Long id, String name) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<BindCardDto> fetchBindCard(Long orgId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<BindCardDto> saveBindCard(Long orgId, BindCardDto bindCardDto) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
