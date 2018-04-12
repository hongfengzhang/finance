package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.activity.PublisherTeleChargeService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.activity.PublisherTeleChargeDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublisherTeleChargeServiceFallback implements PublisherTeleChargeService{
    @Override
    public Response<PublisherTeleChargeDto> savePublisherTeleCharge(PublisherTeleChargeDto publisherTeleChargeDtod) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<List<PublisherTeleChargeDto>> getPublisherTeleChargeList(int pageno, Integer pagesize) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PublisherTeleChargeDto> getPublisherTeleCharge(long publisherTeleChargeId) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }
}
