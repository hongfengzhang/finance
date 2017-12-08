package com.waben.stock.datalayer.buyrecord.business;

import com.waben.stock.datalayer.buyrecord.reference.StockReference;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Created by yuyidi on 2017/12/2.
 * @desc
 */
@Service
public class StockBusiness {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("stockFeignReference")
    private StockReference stockReference;

    public StockDto fetchWithExponentByCode(String code) {
        Response<StockDto> response = stockReference.fetchWithExponentByCode(code);
        if ("200".equals(response.getCode())) {
            //股票信息存在
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }


}
