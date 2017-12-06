package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.buyrecord.BuyRecordService;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;

/**
 * @author Created by yuyidi on 2017/12/2.
 * @desc
 */
@Service
public class BuyRecordBusiness {

    @Autowired
    @Qualifier("buyRecordFeignService")
    private BuyRecordService buyRecordService;


    public BuyRecordDto fetchBuyRecord(Long buyRecord) {
        Response<BuyRecordDto> response = buyRecordService.fetchBuyRecord(buyRecord);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }
}
