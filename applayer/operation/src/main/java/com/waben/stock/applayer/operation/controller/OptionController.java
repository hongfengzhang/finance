package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.StockOptionTradeBusiness;
import com.waben.stock.applayer.operation.warpper.mail.MailService;
import com.waben.stock.interfaces.dto.stockcontent.AmountValueDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.manage.PermissionVo;
import com.waben.stock.interfaces.vo.stockcontent.AmountValueVo;
import com.waben.stock.interfaces.vo.stockoption.StockOptionTradeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("/inquiry/{id}")
    @ResponseBody
    public Response<Boolean> inquiry(@PathVariable Long id){
        Boolean result = stockOptionTradeBusiness.inquiry(id);
        return new Response<>(result);
    }

    @RequestMapping("/purchase/{id}")
    @ResponseBody
    public Response<Boolean> purchase(@PathVariable Long id){
        Boolean result = stockOptionTradeBusiness.purchase(id);
        return new Response<>(result);
    }

    @RequestMapping("/exercise/{id}")
    @ResponseBody
    public Response<Boolean> exercise(@PathVariable Long id){
        Boolean result = stockOptionTradeBusiness.exercise(id);
        return new Response<>(result);
    }

    @RequestMapping("/success/{id}")
    @ResponseBody
    public Response<StockOptionTradeVo> success(@PathVariable Long id){
        StockOptionTradeDto stockOptionTradeDto = stockOptionTradeBusiness.success(id);
        StockOptionTradeVo stockOptionTradeVo = CopyBeanUtils.copyBeanProperties(StockOptionTradeVo.class, stockOptionTradeDto, false);
        return new Response<>(stockOptionTradeVo);
    }

    @RequestMapping("/fail/{id}")
    @ResponseBody
    public Response<StockOptionTradeVo> fail(@PathVariable Long id){
        StockOptionTradeDto stockOptionTradeDto = stockOptionTradeBusiness.fail(id);
        StockOptionTradeVo stockOptionTradeVo = CopyBeanUtils.copyBeanProperties(StockOptionTradeVo.class, stockOptionTradeDto, false);
        return new Response<>(stockOptionTradeVo);
    }
}
