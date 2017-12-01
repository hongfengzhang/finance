package com.waben.stock.interfaces.service.manage;

import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
public interface RoleInterface {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Response<RoleDto> fetchByRoleId(@PathVariable("id") Long id);

}
