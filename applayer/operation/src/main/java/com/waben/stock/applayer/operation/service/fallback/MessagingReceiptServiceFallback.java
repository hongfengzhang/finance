package com.waben.stock.applayer.operation.service.fallback;

import com.waben.stock.applayer.operation.service.message.MessagingReceiptService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.message.MessageReceiptDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.MessageReceiptQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;

public class MessagingReceiptServiceFallback implements MessagingReceiptService {

    @Override
    public Response<MessageReceiptDto> addMessageReceipt(MessageReceiptDto messageReceiptDto) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
    }

    @Override
    public Response<Long> dropMessageReceipt(Long messageReceiptId) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);    }

    @Override
    public Response<MessageReceiptDto> modifyMessageReceipt(MessageReceiptDto messageReceiptDto) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);    }

    @Override
    public Response<MessageReceiptDto> fetchMessageReceiptById(Long messageReceiptId) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);    }

    @Override
    public Response<PageInfo<MessageReceiptDto>> pages(MessageReceiptQuery messageReceiptQuery) {
        return new Response<>(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);    }
}
