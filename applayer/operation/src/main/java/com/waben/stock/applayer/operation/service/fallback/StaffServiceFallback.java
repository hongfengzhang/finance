package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.interfaces.dto.StaffDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Created by yuyidi on 2017/11/15.
 * @desc
 */
//@Component
public class StaffServiceFallback {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Response<StaffDto> fetchByUserName(String username) {
        return new Response<>("205", "用户信息不存在");
    }
}
