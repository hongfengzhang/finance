package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.manage.RoleService;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
@Component
public class RoleServiceFallback implements RoleService {

    @Override
    public Response<Set<RoleDto>> fetchAllByStaff(Long staff) {
        return new Response<>("205","角色信息获取失败");
    }
}
