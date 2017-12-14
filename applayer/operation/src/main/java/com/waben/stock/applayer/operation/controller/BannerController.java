package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.BannerBusiness;
import com.waben.stock.interfaces.dto.manage.BannerDto;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BannerQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PermissionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerBusiness bannerBusiness;

    @RequestMapping("/index")
    public String index() {
        return "manage/banner/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<BannerDto>> pages(BannerQuery query) {
        PageInfo<BannerDto> response = bannerBusiness.pages(query);
        return new Response<>(response);
    }
}
