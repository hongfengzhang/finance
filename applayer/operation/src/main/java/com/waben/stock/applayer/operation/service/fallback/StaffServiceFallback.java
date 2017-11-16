package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.StaffService;
import com.waben.stock.interfaces.dto.StaffDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Created by yuyidi on 2017/11/15.
 * @desc
 */
@Component
public class StaffServiceFallback implements StaffService {

    public Response<StaffDto> fetchByUserName(String username) {
        return new Response<>("205", "用户" + username + "信息不存在");
    }
}
