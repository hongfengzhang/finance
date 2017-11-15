package com.waben.stock.interfaces.service;

import com.waben.stock.interfaces.dto.StaffDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.web.bind.annotation.*;

/**
 * @author Created by yuyidi on 2017/11/15.
 * @desc
 */
public interface StaffInterface {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    Response<StaffDto> fetchByUserName(@RequestParam("username") String username);
}



