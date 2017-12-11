package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.manage.CircularsService;
import com.waben.stock.interfaces.dto.manage.CircularsDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CircularsQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;

/**
 * @author Created by yuyidi on 2017/12/11.
 * @desc
 */
@Service
public class CircularsBusiness {

    @Autowired
    @Qualifier("cricularsFeignService")
    private CircularsService circularsService;

    public PageInfo<CircularsDto> pages(CircularsQuery query) {
        Response<PageInfo<CircularsDto>> response = circularsService.pages(query);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }
}
