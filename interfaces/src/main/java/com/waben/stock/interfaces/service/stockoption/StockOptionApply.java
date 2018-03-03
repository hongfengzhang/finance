package com.waben.stock.interfaces.service.stockoption;

import com.waben.stock.interfaces.dto.buyrecord.SettlementDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionApplyDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.SettlementQuery;
import com.waben.stock.interfaces.pojo.query.StockOptionApplyQuery;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface StockOptionApply {
    /**
     * 分页查询期权申购信息
     *
     * @param query
     *            查询条件
     * @return 结算记录
     */
    @RequestMapping(value = "/pages", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<PageInfo<StockOptionApplyDto>> pagesByQuery(@RequestBody StockOptionApplyQuery query);
}
