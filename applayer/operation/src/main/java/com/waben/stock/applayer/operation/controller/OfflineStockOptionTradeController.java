package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.OfflineStockOptionTradeBusiness;
import com.waben.stock.applayer.operation.business.StockOptionOrgBusiness;
import com.waben.stock.interfaces.dto.stockoption.OfflineStockOptionTradeDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionOrgDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.stockoption.OfflineStockOptionTradeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/offlinestockoptiontrade")
public class OfflineStockOptionTradeController {

    @Autowired
    private OfflineStockOptionTradeBusiness offlineStockOptionTradeBusiness;

    @GetMapping("/add")
    @ResponseBody
    public Response<OfflineStockOptionTradeVo> add(OfflineStockOptionTradeVo offlineStockOptionTradeVo) {
        OfflineStockOptionTradeDto offlineStockOptionTradeDto = CopyBeanUtils.copyBeanProperties(OfflineStockOptionTradeDto.class, offlineStockOptionTradeVo, false);
        offlineStockOptionTradeDto.setOrg(CopyBeanUtils.copyBeanProperties(StockOptionOrgDto.class, offlineStockOptionTradeVo.getOrg(), false));
        OfflineStockOptionTradeVo result = CopyBeanUtils.copyBeanProperties(OfflineStockOptionTradeVo.class, offlineStockOptionTradeBusiness.add(offlineStockOptionTradeDto), false);
        return new Response<>(result);
    }

    @RequestMapping("/settlement/{id}")
    @ResponseBody
    public Response<OfflineStockOptionTradeVo> settlement(@PathVariable Long id, BigDecimal sellingPrice) {
        OfflineStockOptionTradeVo result = CopyBeanUtils.copyBeanProperties(OfflineStockOptionTradeVo.class, offlineStockOptionTradeBusiness.settlement(id,sellingPrice), false);
        return new Response<>(result);
    }
}
