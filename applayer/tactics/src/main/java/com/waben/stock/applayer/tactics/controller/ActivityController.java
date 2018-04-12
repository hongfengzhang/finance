package com.waben.stock.applayer.tactics.controller;

import com.waben.stock.applayer.tactics.business.ActivityBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
