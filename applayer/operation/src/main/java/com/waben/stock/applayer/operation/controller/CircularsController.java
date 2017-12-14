package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.CircularsBusiness;
import com.waben.stock.interfaces.dto.manage.CircularsDto;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CircularsQuery;
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
@RequestMapping("/circulars")
public class CircularsController {

    @Autowired
    private CircularsBusiness circularsBusiness;

    @RequestMapping("/index")
    public String stock() {
        return "manage/circulars/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<CircularsDto>> pages(CircularsQuery query) {
        PageInfo<CircularsDto> response = circularsBusiness.pages(query);
        return new Response<>(response);
    }
}
