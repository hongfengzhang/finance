package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.PermissionBusiness;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PermissionQuery;
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
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionBusiness permissionBusiness;

    @RequestMapping("/index")
    public String stock() {
        return "manage/permission/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<PermissionDto>> pages(PermissionQuery query) {
        PageInfo<PermissionDto> response = permissionBusiness.pages(query);
        return new Response<>(response);
    }
}
