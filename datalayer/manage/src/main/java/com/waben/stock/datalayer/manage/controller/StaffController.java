package com.waben.stock.datalayer.manage.controller;

import com.waben.stock.datalayer.manage.service.StaffService;
import com.waben.stock.interfaces.dto.StaffDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.StaffInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Response<StaffDto> fetchByUserName(String username) {
//        Assert.isNull(username, "用户名不能为空");
        StaffDto staffDto = staffService.fetchByUserName(username);
        return new Response<>(staffDto);
    }

}
