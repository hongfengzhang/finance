package com.waben.stock.applayer.operation.controller;

import com.netflix.discovery.converters.Auto;
import com.waben.stock.applayer.operation.business.RoleBusiness;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.RoleQuery;
import com.waben.stock.interfaces.pojo.query.StockQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Created by yuyidi on 2017/12/11.
 * @desc
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleBusiness roleBusiness;

    @RequestMapping("/index")
    public String stock() {
        return "manage/role/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<RoleDto>> pages(RoleQuery stockQuery) {
        PageInfo<RoleDto> response = roleBusiness.pages(stockQuery);
        return new Response<>(response);
    }
}
