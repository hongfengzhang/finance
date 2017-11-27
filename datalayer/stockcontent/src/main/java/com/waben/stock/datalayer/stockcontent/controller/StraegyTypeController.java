package com.waben.stock.datalayer.stockcontent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waben.stock.datalayer.stockcontent.entity.StrategyType;
import com.waben.stock.datalayer.stockcontent.service.StraegyTypeService;
import com.waben.stock.interfaces.dto.stockcontent.StrategyTypeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.stockcontent.StraegyTypeInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;

/**
 * @author Created by yuyidi on 2017/11/23.
 * @desc
 */
@RestController
@RequestMapping("/straegytype")
public class StraegyTypeController implements StraegyTypeInterface {

    @Autowired
    private StraegyTypeService straegyTypeService;

    @Override
    public Response<List<StrategyTypeDto>> lists(@RequestParam(value = "enable",defaultValue = "false") Boolean enable) {
        List<StrategyType> straegyTypes = straegyTypeService.lists(enable);
        List<StrategyTypeDto> result = CopyBeanUtils.copyListBeanPropertiesToList(straegyTypes, StrategyTypeDto.class);
        return new Response<>(result);
    }

}
