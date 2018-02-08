package com.waben.stock.risk.business;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.risk.service.BuyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/12/14.
 * @desc
 */
@Service
public class BuyRecordBusiness {

    @Autowired
    @Qualifier("buyRecordFeignService")
    private BuyRecordService buyRecordService;

    public List<BuyRecordDto> buyRecordsWithBuyInLock() {
        Response<List<BuyRecordDto>> response = buyRecordService.buyRecordsWithStatus(2);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public List<BuyRecordDto> buyRecordsWithSellOutLock() {
        Response<List<BuyRecordDto>> response = buyRecordService.buyRecordsWithStatus(5);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public List<BuyRecordDto> buyRecordsWithPositionStock() {
        Response<List<BuyRecordDto>> response = buyRecordService.buyRecordsWithStatus(3);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public List<BuyRecordDto> buyRecordsWithWithdrawStock() {
        Response<List<BuyRecordDto>> response = buyRecordService.buyRecordsWithStatus(7);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public List<BuyRecordDto> buyRecordsWithBuyIn() {
        Response<List<BuyRecordDto>> response = buyRecordService.buyRecordsWithStatus(1);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public List<BuyRecordDto> buyRecordsWithSellOut() {
        Response<List<BuyRecordDto>> response = buyRecordService.buyRecordsWithStatus(4);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

}
