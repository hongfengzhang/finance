package com.waben.stock.interfaces.service.stockcontent;

import com.waben.stock.interfaces.dto.stockcontent.StrategyTypeDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/23.
 * @desc
 */
public interface StraegyTypeInterface {

    @RequestMapping(value = "/")
    Response<List<StrategyTypeDto>> lists(@RequestParam(value = "enable", required = false, defaultValue = "false")
                                                 Boolean enable);
}
