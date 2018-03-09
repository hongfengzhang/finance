package com.waben.stock.applayer.operation.service.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.operation.service.publisher.BindCardService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.pojo.Response;
@Component
public class BindCardServiceFallback implements BindCardService {

	@Override
	public Response<BindCardDto> fetchById(Long id) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<BindCardDto> addBankCard(BindCardDto bindCardDto) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<BindCardDto> modifyBankCard(BindCardDto bindCardDto) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<Long> dropBankCard(Long id) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<List<BindCardDto>> listsByResourceTypeAndResourceId(String resourceType, Long resourceId) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
