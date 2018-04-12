package com.waben.stock.applayer.operation.controller;


import com.waben.stock.applayer.operation.business.PublisherBusiness;
import com.waben.stock.applayer.operation.business.PublisherDeduTicketBusiness;
import com.waben.stock.interfaces.dto.activity.PublisherDeduTicketDto;
import com.waben.stock.interfaces.dto.activity.TicketAmountDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/dedu")
public class PublisherDeduTicketController {

    @Autowired
    private PublisherDeduTicketBusiness publisherDeduTicketBusiness;

    @Autowired
    private PublisherBusiness publisherBusiness;

    @RequestMapping("/index")
    public String index() {
        return "winning/dedu/index";
    }
    @GetMapping("/pages")
    @ResponseBody
    public Response<List<PublisherDeduTicketDto>> pages(int pageNo, Integer pageSize) {
        List<PublisherDeduTicketDto> pages = publisherDeduTicketBusiness.pages(pageNo, pageSize);
        for(int i=0; i<pages.size(); i++) {
            PublisherDto publisherDto = publisherBusiness.fetchById(pages.get(i).getPubliserId());
            pages.get(i).setPublisherPhone(publisherDto.getPhone());
        }
        return new Response<>(pages);
    }
}
