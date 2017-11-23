package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.stock.StockService;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Created by yuyidi on 2017/11/22.
 * @desc
 */
@Service
public class StockBusiness {

    @Autowired
    private StockService stockService;

    public PageInfo<StockDto> pages(StockQuery stockQuery) {
        Response<PageInfo<StockDto>> response = stockService.pagesByQuery(stockQuery);
        if (response.getCode().equals("200")) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }

}
