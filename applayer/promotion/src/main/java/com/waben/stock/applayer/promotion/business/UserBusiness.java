package com.waben.stock.applayer.promotion.business;

import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.promotion.reference.organization.UserReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.UserDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;

@Service
public class UserBusiness {

    @Autowired
    @Qualifier("userFeignService")
    private UserReference userReference;

    public UserDto fetchByUserName(String userName) {
        Response<UserDto> response = userReference.fetchByUserName(userName);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }


    public UserDto save(UserDto requestDto) {
        return null;
    }

    public UserDto saveUserRole(Long id, Long[] roleIds) {
        return null;
    }

    public PageInfo<UserDto> pages(UserQuery userQuery) {
        Response<PageInfo<UserDto>> response = userReference.pages(userQuery);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
