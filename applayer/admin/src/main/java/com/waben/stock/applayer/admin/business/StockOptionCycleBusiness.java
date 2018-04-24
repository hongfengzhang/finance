package com.waben.stock.applayer.admin.business;


import com.waben.stock.applayer.admin.reference.StockOptionCycleReference;
import com.waben.stock.interfaces.dto.stockoption.StockOptionCycleDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockOptionCycleBusiness {

    @Autowired
    private StockOptionCycleReference reference;

    public List<StockOptionCycleDto> findAll() {
        Response<List<StockOptionCycleDto>> response = reference.lists();
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }

}
