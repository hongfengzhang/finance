package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.ActivityBusiness;
import com.waben.stock.interfaces.dto.activity.ActivityDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityBusiness activityBusiness;

    @RequestMapping("/index")
    public String index() {
        return "activity/manage/index";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Response<ActivityDto> fetchActivityById(@PathVariable long id) {
        ActivityDto activityDto = activityBusiness.findActivityById(id);
        return new Response<>(activityDto);
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<List<ActivityDto>> pages(int pageNo,Integer pageSize) {
        List<ActivityDto> pages = activityBusiness.pages(pageNo, pageSize);
        return new Response<>(pages);
    }
}
