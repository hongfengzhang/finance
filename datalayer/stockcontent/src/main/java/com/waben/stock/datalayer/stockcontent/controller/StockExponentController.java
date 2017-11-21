package com.waben.stock.datalayer.stockcontent.controller;

import com.waben.stock.datalayer.stockcontent.entity.StockExponent;
import com.waben.stock.datalayer.stockcontent.service.StockExponentService;
import com.waben.stock.interfaces.dto.stockcontent.StockExponentDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.stockcontent.StockExponentInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/21.
 * @desc
 */
@RestController
@RequestMapping("/stockexponent")
public class StockExponentController implements StockExponentInterface{

    @Autowired
    private StockExponentService stockExponentService;

    @Override
    public Response<List<StockExponentDto>> fetchStockExponents() {
        List<StockExponent> stockExponents = stockExponentService.findStockExponts();
        List<StockExponentDto> stockExponentDtos = CopyBeanUtils.copyListBeanPropertiesToList(stockExponents,
                StockExponentDto.class);
        return new Response<>(stockExponentDtos);
    }
}
