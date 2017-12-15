package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.manage.BannerService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.BannerDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BannerQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BannerServiceFallback implements BannerService {

    @Override
    public Response<List<BannerDto>> fetchBanners(Boolean enable) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PageInfo<BannerDto>> pages(BannerQuery query) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
