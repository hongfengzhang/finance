package com.waben.stock.applayer.promotion.business;

import com.waben.stock.applayer.promotion.reference.manage.PermissionReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionBusiness {
    @Autowired
    @Qualifier("permissionReference")
    private PermissionReference permissionReference;

    public List<PermissionDto> findPermissionsByVariety() {
        Response<List<PermissionDto>> response = permissionReference.fetchPermissionsByVariety(4L);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public List<PermissionDto> fetchPermissionsByRole(Long role) {
        Response<List<PermissionDto>> response = permissionReference.fetchByRole(role);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
