package com.waben.stock.interfaces.service.manage;

import com.waben.stock.interfaces.dto.RoleDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Set;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
public interface RoleInterface {

    @RequestMapping(value = "/staff/{staff}", method = RequestMethod.GET)
    Response<Set<RoleDto>> fetchAllByStaff(@PathVariable("staff") Long staff);
}
