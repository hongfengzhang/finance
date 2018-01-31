package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.manage.RoleService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.RoleQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
@Component
public class RoleServiceFallback implements RoleService {

    @Override
    public Response<RoleDto> role(Long id) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }


    @Override
    public Response<PageInfo<RoleDto>> pages(RoleQuery query) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<RoleDto> fetchById(Long id) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<RoleDto> modify(RoleDto roleDto) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Response<RoleDto> add(RoleDto roleDto) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<List<RoleDto>> fetchRoles() {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);    }
}
