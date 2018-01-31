package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.message.MessagingReceiptService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.message.MessageReceiptDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MessagingReceiptBusiness {
    @Autowired
    @Qualifier("messagingReceiptFeignService")
    private MessagingReceiptService messagingReceiptService;

    public MessageReceiptDto save(MessageReceiptDto messageReceiptDto) {
        Response<MessageReceiptDto> response = messagingReceiptService.addMessageReceipt(messageReceiptDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
