package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.activity.TicketAmountService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.activity.TicketAmountDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketAmountBusiness {
    @Autowired
    private TicketAmountService ticketAmountService;

    public List<TicketAmountDto> pages(int pageNo, Integer pageSize) {
        Response<List<TicketAmountDto>> response = ticketAmountService.getTicketAmountList(pageNo, pageSize);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public TicketAmountDto save(TicketAmountDto ticketAmountDto) {
        Response<TicketAmountDto> response = ticketAmountService.saveTicketAmount(ticketAmountDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public Void remove(long ticketAmountId) {
        Response<Void> response = ticketAmountService.deleteTicket(ticketAmountId);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public TicketAmountDto findTicketAmountById(long ticketAmountId) {
        Response<TicketAmountDto> response = ticketAmountService.getTicketAmount(ticketAmountId);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public TicketAmountDto revision(TicketAmountDto ticketAmountDto) {
        Response<TicketAmountDto> response = ticketAmountService.saveTicketAmount(ticketAmountDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
