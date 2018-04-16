package com.waben.stock.applayer.tactics.business;

import com.waben.stock.applayer.tactics.reference.TicketAmountReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.activity.TicketAmountDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketAmountBusiness {

    @Autowired
    private TicketAmountReference ticketAmountReference;

    public TicketAmountDto findTicketAmountById(long ticketAmountId) {
        Response<TicketAmountDto> response = ticketAmountReference.getTicketAmount(ticketAmountId);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

}
