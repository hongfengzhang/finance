package com.waben.stock.applayer.admin.reference.fallback;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.admin.reference.DrawActivityReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.activity.DrawActivityDto;
import com.waben.stock.interfaces.dto.activity.TicketAmountDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;

@Component
public class DrawActivityReferenceFallback implements DrawActivityReference {
    @Override
    public Response<TicketAmountDto> draw(long activityId, long publisherId) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<DrawActivityDto> getDrawActicity(long activityId, long publisherId) {
        throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
