package com.waben.stock.applayer.admin.reference.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.admin.reference.DrawActivityRadioReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.activity.DrawActivityRadioDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;

@Component
public class DrawActivityRadioReferenceFallback implements DrawActivityRadioReference{
    @Override
    public Response<List<DrawActivityRadioDto>> getDrawActivityRadiosByActivityId(long activityId) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<DrawActivityRadioDto> getDrawActivityRadioByTicketId(long ticketId) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
