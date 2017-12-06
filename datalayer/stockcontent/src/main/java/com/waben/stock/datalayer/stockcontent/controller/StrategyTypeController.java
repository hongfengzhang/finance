package com.waben.stock.datalayer.stockcontent.controller;

import java.util.List;

import com.waben.stock.datalayer.stockcontent.entity.Stock;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StrategyTypeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.stockcontent.entity.StrategyType;
import com.waben.stock.datalayer.stockcontent.service.StrategyTypeService;
import com.waben.stock.interfaces.dto.stockcontent.StrategyTypeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.stockcontent.StrategyTypeInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;

/**
 * @author Created by yuyidi on 2017/11/23.
 * @desc
 */
@RestController
@RequestMapping("/strategytype")
public class StrategyTypeController implements StrategyTypeInterface {

    @Autowired
    private StrategyTypeService straegyTypeService;

    @Override
    public Response<List<StrategyTypeDto>> lists(@RequestParam(value = "enable",defaultValue = "false") Boolean enable) {
        List<StrategyType> straegyTypes = straegyTypeService.lists(enable);
        List<StrategyTypeDto> result = CopyBeanUtils.copyListBeanPropertiesToList(straegyTypes, StrategyTypeDto.class);
        return new Response<>(result);
    }

    @Override
    public Response<PageInfo<StrategyTypeDto>> pages(StrategyTypeQuery query) {
        Page<StrategyType> pages = straegyTypeService.pages(query);
        PageInfo<StrategyTypeDto> result = new PageInfo<>(pages, StrategyTypeDto.class);
        return new Response<>(result);
    }
}
