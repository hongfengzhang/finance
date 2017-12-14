package com.waben.stock.applayer.strategist.service.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.strategist.service.BannerService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.BannerDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BannerQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;

/**
 * 轮播 断路器回调
 * 
 * @author luomengan
 *
 */
@Component
public class BannerServiceFallback implements BannerService {

	@Override
	public Response<List<BannerDto>> fetchBanners(Boolean enable) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

	@Override
	public Response<PageInfo<BannerDto>> pages(BannerQuery query) {
		throw new ServiceException(ExceptionConstant.UNKNOW_EXCEPTION);
	}

}
