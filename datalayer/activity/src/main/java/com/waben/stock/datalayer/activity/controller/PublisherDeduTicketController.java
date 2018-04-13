package com.waben.stock.datalayer.activity.controller;

import com.waben.stock.datalayer.activity.entity.PublisherDeduTicket;
import com.waben.stock.datalayer.activity.service.PublisherDeduTicketService;
import com.waben.stock.interfaces.dto.activity.PublisherDeduTicketDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.activity.PublisherDeduTicketInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/publisherdeduticket")
public class PublisherDeduTicketController implements PublisherDeduTicketInterface{


    @Autowired
    private PublisherDeduTicketService publisherDeduTicketService;

    @Override
    public Response<PublisherDeduTicketDto> savePublisherDeduTicket(@RequestBody PublisherDeduTicketDto publisherDeduTicketDto) {
        PublisherDeduTicketDto result = publisherDeduTicketService.savePublisherDeduTicket(publisherDeduTicketDto);
        return new Response<>(result);
    }

    @Override
    public Response<List<PublisherDeduTicketDto>> getPublisherDeduTicketList(int pageno, Integer pagesize) {
        List<PublisherDeduTicketDto> result = publisherDeduTicketService.getPublisherDeduTicketList(pageno, pagesize);
        return new Response<>(result);
    }

    @Override
    public Response<PublisherDeduTicketDto> getPublisherDeduTicket(@PathVariable long publisherDeduTicketId) {
        PublisherDeduTicket result = publisherDeduTicketService.getPublisherDeduTicket(publisherDeduTicketId);
        PublisherDeduTicketDto publisherDeduTicketDto = CopyBeanUtils.copyBeanProperties(PublisherDeduTicketDto.class, result, false);
        return new Response<>(publisherDeduTicketDto);
    }

    @Override
    public Response<PublisherDeduTicketDto> getPublisherDeduTicketByApId(@PathVariable long apId) {
        PublisherDeduTicket result = publisherDeduTicketService.getPublisherDeduTicketByApId(apId);
        PublisherDeduTicketDto publisherDeduTicketDto = CopyBeanUtils.copyBeanProperties(PublisherDeduTicketDto.class, result, false);
        return new Response<>(publisherDeduTicketDto);
    }
}
