package com.waben.stock.datalayer.activity.controller;

import com.waben.stock.datalayer.activity.entity.PublisherTicket;
import com.waben.stock.datalayer.activity.service.PublisherTicketService;
import com.waben.stock.interfaces.dto.activity.PublisherTicketDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.activity.PublisherTicketInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/publisherticket")
public class PublisherTicketController implements PublisherTicketInterface{

    @Autowired
    private PublisherTicketService publisherTicketService;

    @Override
    public Response<PublisherTicketDto> savePublisherTicket(PublisherTicketDto publisherTicketDto) {
        PublisherTicketDto result = publisherTicketService.savePublisherTicket(publisherTicketDto);
        return new Response<>(result);
    }

    @Override
    public Response<List<PublisherTicketDto>> getPublisherTicketList(int pageno, Integer pagesize) {
        List<PublisherTicketDto> result = publisherTicketService.getPublisherTicketList(pageno, pagesize);
        return new Response<>(result);
    }

    @Override
    public Response<PublisherTicketDto> getPublisherTicket(long publisherTicketId) {
        PublisherTicket result = publisherTicketService.getPublisherTicket(publisherTicketId);
        PublisherTicketDto publisherTicketDto = CopyBeanUtils.copyBeanProperties(PublisherTicketDto.class, result, false);
        return new Response<>(publisherTicketDto);
    }
}
