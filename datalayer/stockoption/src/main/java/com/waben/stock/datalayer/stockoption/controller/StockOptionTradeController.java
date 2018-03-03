package com.waben.stock.datalayer.stockoption.controller;

import com.waben.stock.datalayer.stockoption.entity.StockOptionTrade;
import com.waben.stock.datalayer.stockoption.service.StockOptionTradeService;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeQuery;
import com.waben.stock.interfaces.service.stockoption.StockOptionTradeInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.PageToPageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stockoptiontrade")
public class StockOptionTradeController implements StockOptionTradeInterface{
    @Autowired
    private StockOptionTradeService stockOptionTradeService;
    @Override
    public Response<PageInfo<StockOptionTradeDto>> pagesByQuery(StockOptionTradeQuery query) {
        Page<StockOptionTrade> page = stockOptionTradeService.pagesByQuery(query);
        PageInfo<StockOptionTradeDto> result = PageToPageInfo.pageToPageInfo(page, StockOptionTradeDto.class);
        return new Response<>(result);
    }
}
