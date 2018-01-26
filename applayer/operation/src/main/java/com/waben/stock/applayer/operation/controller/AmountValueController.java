package com.waben.stock.applayer.operation.controller;

import com.rabbitmq.tools.json.JSONUtil;
import com.waben.stock.applayer.operation.business.AmountValueBusiness;
import com.waben.stock.applayer.operation.business.LossBusiness;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.investor.SecurityAccountDto;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.dto.stockcontent.AmountValueDto;
import com.waben.stock.interfaces.dto.stockcontent.StrategyTypeDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.AmountValueQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.vo.investor.InvestorVo;
import com.waben.stock.interfaces.vo.investor.SecurityAccountVo;
import com.waben.stock.interfaces.vo.stockcontent.AmountValueVo;
import com.waben.stock.interfaces.vo.stockcontent.StrategyTypeVo;
import org.codehaus.jackson.annotate.JsonSetter;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JsonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.GsonFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/amountvalue")
public class AmountValueController {

    @Autowired
    private AmountValueBusiness amountValueBusiness;

    @GetMapping("/index")
    public String index() {
        return "/stock/amountvalue/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<AmountValueVo>> pages(AmountValueQuery query) {
        PageInfo<AmountValueDto> pageInfo = amountValueBusiness.pages(query);
        List<AmountValueVo> amountValueVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), AmountValueVo.class);
        PageInfo<AmountValueVo> response = new PageInfo<>(amountValueVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Long id,ModelMap map){
        AmountValueDto amountValueDto = amountValueBusiness.fetchById(id);
        AmountValueVo amountValueVo = CopyBeanUtils.copyBeanProperties(AmountValueVo.class, amountValueDto, false);
        map.addAttribute("amount", amountValueVo);
        return "stock/amountvalue/view";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, ModelMap map){
        AmountValueDto amountValueDto = amountValueBusiness.fetchById(id);
        AmountValueVo amountValueVo = CopyBeanUtils.copyBeanProperties(AmountValueVo.class, amountValueDto, false);
        map.addAttribute("amountValue", amountValueVo);
        return "stock/amountvalue/edit";
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Response<AmountValueVo> modify(AmountValueVo vo){
        AmountValueDto requestDto = CopyBeanUtils.copyBeanProperties(AmountValueDto.class, vo, false);
        AmountValueDto responseDto = amountValueBusiness.revision(requestDto);
        AmountValueVo result = CopyBeanUtils.copyBeanProperties(AmountValueVo.class, responseDto, false);
        return new Response<>(result);
    }
}
