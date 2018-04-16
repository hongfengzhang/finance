package com.waben.stock.datalayer.activity.controller;


import com.waben.stock.datalayer.activity.entity.TicketAmount;
import com.waben.stock.datalayer.activity.service.DrawActivityService;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("drawactivity")
public class DrawActivityController {

    @Autowired
    private DrawActivityService drawActivityService;

    @RequestMapping("/draw/{activityId}/{publisherId}")
    public Response<TicketAmount> draw(@PathVariable long activityId, @PathVariable long publisherId) {

        TicketAmount ticketAmount = drawActivityService.lotteryDraw(activityId, publisherId);
        return new Response<>(ticketAmount);
    }

}
