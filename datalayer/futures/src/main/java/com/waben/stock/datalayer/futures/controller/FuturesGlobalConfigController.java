package com.waben.stock.datalayer.futures.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.futures.entity.FuturesGlobalConfig;
import com.waben.stock.datalayer.futures.service.FuturesGlobalConfigService;
import com.waben.stock.interfaces.dto.admin.futures.FuturesGlobalConfigDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.futures.FuturesGlobalConfigQuery;
import com.waben.stock.interfaces.service.futures.FuturesGlobalConfigInterface;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/globalConfig")
@Api(description = "风控-警戒线  接口列表")
public class FuturesGlobalConfigController implements FuturesGlobalConfigInterface {

	@Autowired
	private FuturesGlobalConfigService configService;
	
	@Override
	public Response<FuturesGlobalConfigDto> addConfig(FuturesGlobalConfigDto query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<FuturesGlobalConfigDto> modifyConfig(FuturesGlobalConfigDto query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteConfig(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Response<PageInfo<FuturesGlobalConfigDto>> pagesConfig(FuturesGlobalConfigQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<PageInfo<FuturesGlobalConfigDto>> findAll() {
//		Page<FuturesGlobalConfig> page = configService.
		return null;
	}

}
