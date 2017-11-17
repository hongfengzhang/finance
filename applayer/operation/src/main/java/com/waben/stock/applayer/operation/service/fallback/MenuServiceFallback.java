package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.manage.MenuService;
import com.waben.stock.interfaces.dto.MenuDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/17.
 * @desc
 */
@Component
public class MenuServiceFallback implements MenuService {
    @Override
    public Response<List<MenuDto>> menusByRole(Long role) {
        return new Response<>("205","暂无菜单数据");
    }

    @Override
    public Response<List<MenuDto>> menusByStaff(Long staff) {
        return new Response<>("205","暂无菜单数据");
    }
}
