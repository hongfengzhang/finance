package com.waben.stock.datalayer.stockoption.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.stockoption.entity.OfflineStockOptionTrade;
import com.waben.stock.datalayer.stockoption.entity.StockOptionOrg;
import com.waben.stock.datalayer.stockoption.service.OfflineStockOptionTradeService;
import com.waben.stock.interfaces.dto.stockoption.OfflineStockOptionTradeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.stockoption.OfflineStockOptionTradeInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;

@RestController
@RequestMapping("/offlinestockoptiontrade")
public class OfflineStockOptionTradeController implements OfflineStockOptionTradeInterface {
    @Autowired
    private OfflineStockOptionTradeService offlineStockOptionTradeService;

    @Override
    public Response<OfflineStockOptionTradeDto> add(@RequestBody OfflineStockOptionTradeDto
                                                                offlineStockOptionTradeDto) {
        OfflineStockOptionTrade offlineStockOptionTrade = CopyBeanUtils.copyBeanProperties(OfflineStockOptionTrade
                .class, offlineStockOptionTradeDto, false);
        offlineStockOptionTrade.setOrg(CopyBeanUtils.copyBeanProperties(StockOptionOrg.class,
                offlineStockOptionTradeDto.getOrg(), false));
        OfflineStockOptionTradeDto result = CopyBeanUtils.copyBeanProperties(OfflineStockOptionTradeDto.class,
                offlineStockOptionTradeService.save(offlineStockOptionTrade), false);
        return new Response<>(result);
    }

    @Override
    public Response<OfflineStockOptionTradeDto> settlement(@PathVariable Long id,@PathVariable BigDecimal sellingPrice) {
        OfflineStockOptionTradeDto result = CopyBeanUtils.copyBeanProperties(OfflineStockOptionTradeDto.class, offlineStockOptionTradeService.settlement(id,sellingPrice), false);
        return new Response<>(result);
    }

    @Override
    public Response<OfflineStockOptionTradeDto> exercise(@PathVariable Long id) {
        OfflineStockOptionTrade result = offlineStockOptionTradeService.exercise(id);
        OfflineStockOptionTradeDto offlineStockOptionTradeDto = CopyBeanUtils.copyBeanProperties(OfflineStockOptionTradeDto.class, result, false);
        return new Response<>(offlineStockOptionTradeDto);
    }

    @Override
    public Response<OfflineStockOptionTradeDto> find(@PathVariable Long id) {
        OfflineStockOptionTrade result = offlineStockOptionTradeService.findById(id);
        OfflineStockOptionTradeDto response = CopyBeanUtils.copyBeanProperties(OfflineStockOptionTradeDto.class,
                result, false);
        return new Response<>(response);
    }


}
