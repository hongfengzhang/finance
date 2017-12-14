package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.manage.PermissionService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PermissionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Created by yuyidi on 2017/12/11.
 * @desc
 */
@Service
public class PermissionBusiness {

    @Autowired
    @Qualifier("permissionFeignService")
    private PermissionService permissionService;

    public PageInfo<PermissionDto> pages(PermissionQuery query) {
        Response<PageInfo<PermissionDto>> response = permissionService.pages(query);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

}
