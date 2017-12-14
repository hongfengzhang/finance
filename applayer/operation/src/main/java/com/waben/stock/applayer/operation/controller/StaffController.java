package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.StaffBusiness;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StaffQuery;
import com.waben.stock.interfaces.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Created by yuyidi on 2017/11/19.
 * @desc
 */
@Controller
@RequestMapping("/staff")
public class StaffController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StaffBusiness staffBusiness;

    @RequestMapping("/index")
    public String user() {
        return "manage/staff/index";
    }

    @RequestMapping("/pages")
    @ResponseBody
    public Response<PageInfo<StaffDto>> pages(StaffQuery staffQuery) {
        PageInfo<StaffDto> pages = staffBusiness.staffs(staffQuery);
        return new Response<>(pages);
    }
}
