package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.activity.TicketAmountService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.activity.TicketAmountDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class TicketAmountServiceFallback implements TicketAmountService {
    @Override
    public Response<TicketAmountDto> saveTicketAmount(TicketAmountDto td) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PageInfo<TicketAmountDto>> getTicketAmountList(int pageno, Integer pagesize) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<Void> deleteTicket(long ticketId) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<TicketAmountDto> getTicketAmount(long ticketAmountId) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
