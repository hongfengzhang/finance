package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.stock.StockService;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Created by yuyidi on 2017/11/22.
 * @desc
 */
@Service
public class StockBusiness {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StockService stockService;

    public PageInfo<StockDto> pages(StockQuery stockQuery) {
        Response<PageInfo<StockDto>> response = stockService.pagesByQuery(stockQuery);
        if (response.getCode().equals("200")) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }

    public StockDto fetchWithExponentByCode(String code) {
        Response<StockDto> response = stockService.fetchWithExponentByCode(code);
        if ("200".equals(response.getCode())) {
            //股票信息存在
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }

}
