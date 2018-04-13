package com.waben.stock.applayer.tactics.reference.fallback;

import com.waben.stock.applayer.tactics.reference.PublisherDeduTicketReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.activity.PublisherDeduTicketDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublisherDeduTicketReferenceFallback implements PublisherDeduTicketReference{

    @Override
    public Response<PublisherDeduTicketDto> savePublisherDeduTicket(PublisherDeduTicketDto publisherDeduTicketDto) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<List<PublisherDeduTicketDto>> getPublisherDeduTicketList(int pageno, Integer pagesize) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PublisherDeduTicketDto> getPublisherDeduTicket(long publisherDeduTicketId) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
