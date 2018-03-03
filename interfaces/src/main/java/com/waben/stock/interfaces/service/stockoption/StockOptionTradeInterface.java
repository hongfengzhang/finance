package com.waben.stock.interfaces.service.stockoption;

import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeQuery;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

public interface StockOptionTradeInterface {
    /**
     * 分页查询期权申购信息
     *
     * @param query
     *            查询条件
     * @return 结算记录
     */
    @RequestMapping(value = "/pages", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<PageInfo<StockOptionTradeDto>> pagesByQuery(@RequestBody StockOptionTradeQuery query);

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Response<StockOptionTradeDto> fetchById(@PathVariable("id") Long id);
}
