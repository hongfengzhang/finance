package com.waben.stock.interfaces.service.stockcontent;

import com.waben.stock.interfaces.dto.stockcontent.StrategyTypeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StrategyTypeQuery;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/23.
 * @desc
 */
public interface StrategyTypeInterface {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    Response<List<StrategyTypeDto>> lists(
            @RequestParam(value = "enable", required = false, defaultValue = "false") Boolean enable);

    @RequestMapping(value = "/pages", method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<PageInfo<StrategyTypeDto>> pages(@RequestBody StrategyTypeQuery query);
}
