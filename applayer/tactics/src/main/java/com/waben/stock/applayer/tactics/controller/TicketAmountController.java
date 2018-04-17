package com.waben.stock.applayer.tactics.controller;


import com.waben.stock.applayer.tactics.business.DrawActivityRadioBusiness;
import com.waben.stock.applayer.tactics.business.TicketAmountBusiness;
import com.waben.stock.interfaces.dto.activity.DrawActivityRadioDto;
import com.waben.stock.interfaces.dto.activity.TicketAmountDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketAmountController {

    @Autowired
    private DrawActivityRadioBusiness drawActivityRadioBusiness;

    @Autowired
    private TicketAmountBusiness ticketAmountBusiness;
    
    @GetMapping("/tickets/{activityId}")
    public Response<List<TicketAmountDto>> fetchTicketAmountsById(@PathVariable long activityId) {
        List<DrawActivityRadioDto> result = drawActivityRadioBusiness.getDrawActivityRadiosByActivityId(activityId);
        List<TicketAmountDto> ticketAmountDtos = new ArrayList<>();
        for(DrawActivityRadioDto drawActivityRadioDto : result) {
            TicketAmountDto ticketAmountDto = ticketAmountBusiness.findTicketAmountById(drawActivityRadioDto.getTicketId());
            ticketAmountDtos.add(ticketAmountDto);
        }
        return new Response<>(ticketAmountDtos);
    }
    
}
