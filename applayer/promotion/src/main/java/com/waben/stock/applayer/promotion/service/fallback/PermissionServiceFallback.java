package com.waben.stock.applayer.promotion.service.fallback;

import com.waben.stock.applayer.promotion.service.manage.PermissionService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PermissionQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionServiceFallback implements PermissionService {

    @Override
    public Response<PermissionDto> permission(Long id) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);

    }

    @Override
    public Response<PageInfo<PermissionDto>> pages(PermissionQuery query) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PermissionDto> modify(PermissionDto requestDto) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Response<PermissionDto> add(PermissionDto requestDto) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<List<PermissionDto>> fetchPermissions() {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
