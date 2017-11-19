package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.manage.StaffService;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StaffQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * @author Created by yuyidi on 2017/11/15.
 * @desc
 */
@Component
public class StaffServiceFallback implements StaffService {

    public Response<StaffDto> fetchByUserName(String username) {
        return new Response<>("205", "用户" + username + "信息不存在");
    }

    @Override
    public Response<PageInfo<StaffDto>> pagesByQuery(StaffQuery staffQuery) {
        return new Response<>("205","用户列表服务异常");
    }
}
