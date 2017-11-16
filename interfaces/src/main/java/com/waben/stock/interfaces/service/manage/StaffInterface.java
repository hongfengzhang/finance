package com.waben.stock.interfaces.service.manage;

import com.waben.stock.interfaces.dto.StaffDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.web.bind.annotation.*;

/**
 * @author Created by yuyidi on 2017/11/15.
 * @desc
 */
public interface StaffInterface {

    @RequestMapping(value = "/{username}",method = RequestMethod.GET)
    Response<StaffDto> fetchByUserName(@PathVariable("username") String username);
}



