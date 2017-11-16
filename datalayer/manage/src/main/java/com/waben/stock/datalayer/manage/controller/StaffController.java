package com.waben.stock.datalayer.manage.controller;

import com.waben.stock.datalayer.manage.entity.Role;
import com.waben.stock.datalayer.manage.entity.Staff;
import com.waben.stock.datalayer.manage.service.RoleService;
import com.waben.stock.datalayer.manage.service.StaffService;
import com.waben.stock.interfaces.dto.RoleDto;
import com.waben.stock.interfaces.dto.StaffDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.manage.StaffInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/15.
 * @desc
 */
@RestController
@RequestMapping("/staff")
public class StaffController implements StaffInterface {

    @Autowired
    private StaffService staffService;

    @Override
    public Response<StaffDto> fetchByUserName(@PathVariable String username) {
        Staff staff = staffService.fetchByUserName(username);
        StaffDto staffDto = CopyBeanUtils.copyBeanProperties(staff,new StaffDto(),false);
        return new Response<>(staffDto);
    }
}
