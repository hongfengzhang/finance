package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.StockOptionTradeBusiness;
import com.waben.stock.interfaces.dto.stockcontent.AmountValueDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.stockcontent.AmountValueVo;
import com.waben.stock.interfaces.vo.stockoption.StockOptionTradeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/option")
public class OptionController {

    @Autowired
    private StockOptionTradeBusiness stockOptionTradeBusiness;
    @RequestMapping("/index")
    public String index(){
        return "options/index";
    }

    @RequestMapping("/pages")
    @ResponseBody
    public Response<PageInfo<StockOptionTradeVo>> pages(StockOptionTradeQuery query){
        PageInfo<StockOptionTradeDto> pageInfo = stockOptionTradeBusiness.pages(query);
        List<StockOptionTradeVo> stockOptionTradeVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), StockOptionTradeVo.class);
        PageInfo<StockOptionTradeVo> response = new PageInfo<>(stockOptionTradeVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }
}
