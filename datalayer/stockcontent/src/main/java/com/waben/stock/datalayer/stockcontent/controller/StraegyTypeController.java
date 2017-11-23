package com.waben.stock.datalayer.stockcontent.controller;

import com.waben.stock.datalayer.stockcontent.entity.StraegyType;
import com.waben.stock.datalayer.stockcontent.service.StraegyTypeService;
import com.waben.stock.interfaces.dto.stockcontent.StraegyTypeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.stockcontent.StraegyTypeInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public Response<List<StraegyTypeDto>> lists(@RequestParam(value = "enable",defaultValue = "false") Boolean enable) {
        List<StraegyType> straegyTypes = straegyTypeService.lists(enable);
        List<StraegyTypeDto> result = CopyBeanUtils.copyListBeanPropertiesToList(straegyTypes, StraegyTypeDto.class);
        return new Response<>(result);
    }

}
