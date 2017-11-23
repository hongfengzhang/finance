package com.waben.stock.interfaces.service.stockcontent;

import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockQuery;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/***
 * @author yuyidi 2017-11-22 10:06:54
 * @class com.waben.stock.interfaces.service.stockcontent.StockInterface
 * @description
 */
public interface StockInterface {

    @RequestMapping(value = "/pages", method = RequestMethod.GET, consumes = "application/json")
    Response<PageInfo<StockDto>> pagesByQuery(@RequestBody StockQuery stockQuery);
}
