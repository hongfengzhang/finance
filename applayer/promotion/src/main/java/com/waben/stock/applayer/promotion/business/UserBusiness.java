package com.waben.stock.applayer.promotion.business;

import com.waben.stock.applayer.promotion.service.organization.UserService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.organization.UserDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserBusiness {

    @Autowired
    @Qualifier("userFeignService")
    private UserService userReference;

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
}
