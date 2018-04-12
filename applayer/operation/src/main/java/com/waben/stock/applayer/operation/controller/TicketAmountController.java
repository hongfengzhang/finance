package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.TicketAmountBusiness;
import com.waben.stock.interfaces.dto.activity.ActivityDto;
import com.waben.stock.interfaces.dto.activity.TicketAmountDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketAmountController {
    @Autowired
    private TicketAmountBusiness  ticketAmountBusiness;
    @RequestMapping("/index")
    public String index() {
        return "activity/ticket/index";
    }
    @GetMapping("/pages")
    @ResponseBody
    public Response<List<TicketAmountDto>> pages(int pageNo, Integer pageSize) {
        List<TicketAmountDto> pages = ticketAmountBusiness.pages(pageNo, pageSize);
        return new Response<>(pages);
    }

    @RequestMapping("/add")
    public String add() {
        return "activity/ticket/add";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Response<TicketAmountDto> add(TicketAmountDto ticketAmountDto) {
        TicketAmountDto result = ticketAmountBusiness.save(ticketAmountDto);
        return new Response<>(result);
    }
}
