package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.manage.RoleService;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.RoleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Created by yuyidi on 2017/12/11.
 * @desc
 */
@Service
public class RoleBusiness {

    @Autowired
    private RoleService roleService;

    public PageInfo<RoleDto> pages(RoleQuery query) {
        Response<PageInfo<RoleDto>> response = roleService.pages(query);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }
}
