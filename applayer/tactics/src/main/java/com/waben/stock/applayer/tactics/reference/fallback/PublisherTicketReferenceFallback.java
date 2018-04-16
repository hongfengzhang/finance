package com.waben.stock.applayer.tactics.reference.fallback;

import com.waben.stock.applayer.tactics.reference.PublisherTicketReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.activity.PublisherTicketDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublisherTicketReferenceFallback implements PublisherTicketReference {
    @Override
    public Response<PublisherTicketDto> savePublisherTicket(PublisherTicketDto publisherTicketDto) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<List<PublisherTicketDto>> getPublisherTicketList(int pageno, Integer pagesize) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PublisherTicketDto> getPublisherTicket(long publisherTicketId) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

	@Override
	public Response<PublisherTicketDto> getPublisherTicketByApId(long apId) {
		return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}
}
