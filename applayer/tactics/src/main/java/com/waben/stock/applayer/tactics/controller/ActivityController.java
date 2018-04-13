package com.waben.stock.applayer.tactics.controller;

import com.waben.stock.applayer.tactics.business.ActivityBusiness;
import com.waben.stock.interfaces.dto.activity.ActivityDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 活动接入
 * 
 * @author guowei
 *
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityBusiness activityBusiness;



    @GetMapping("/{id}")
    public Response<ActivityDto> fetchActivityById(@PathVariable long id) {
        ActivityDto activityDto = activityBusiness.findActivityById(id);
        return new Response<>(activityDto);
    }

}
