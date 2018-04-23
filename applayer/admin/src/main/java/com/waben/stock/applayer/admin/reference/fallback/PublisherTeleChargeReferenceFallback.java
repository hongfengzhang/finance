package com.waben.stock.applayer.admin.reference.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.waben.stock.applayer.admin.reference.PublisherTeleChargeReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.activity.PublisherTeleChargeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;

@Component
public class PublisherTeleChargeReferenceFallback implements PublisherTeleChargeReference {
    @Override
    public Response<PublisherTeleChargeDto> savePublisherTeleCharge(PublisherTeleChargeDto publisherTeleChargeDtod) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PageInfo<PublisherTeleChargeDto>> getPublisherTeleChargeList(int pageno, Integer pagesize) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<PublisherTeleChargeDto> getPublisherTeleCharge(long publisherTeleChargeId) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<Void> setPay(long publisherTeleChargeId) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    public Response<List<PublisherTeleChargeDto>> getPublisherTeleChargesByApId(long apId) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

}
