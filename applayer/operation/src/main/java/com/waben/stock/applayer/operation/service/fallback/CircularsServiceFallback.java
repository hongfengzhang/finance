package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.manage.CircularsService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.CircularsDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CircularsQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/12/11.
 * @desc
 */
@Component
public class CircularsServiceFallback implements CircularsService {

    @Override
    public Response<List<CircularsDto>> fetchCirculars(Boolean enable) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PageInfo<CircularsDto>> pages(CircularsQuery query) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
