package com.waben.stock.datalayer.stockcontent.controller;

import com.waben.stock.datalayer.stockcontent.entity.AmountValue;
import com.waben.stock.datalayer.stockcontent.entity.Loss;
import com.waben.stock.datalayer.stockcontent.service.AmountValueService;
import com.waben.stock.interfaces.dto.stockcontent.AmountValueDto;
import com.waben.stock.interfaces.dto.stockcontent.LossDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.AmountValueQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.stockcontent.AmountValueInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/amountvalue")
public class AmountValueController implements AmountValueInterface{

    @Autowired
    private AmountValueService amountValueService;

    @Override
    public Response<PageInfo<AmountValueDto>> pagesByQuery(@RequestBody AmountValueQuery amountValueQuery) {
        Page<AmountValue> amountValues = amountValueService.pages(amountValueQuery);
        PageInfo<AmountValueDto> result = new PageInfo<>(amountValues, AmountValueDto.class);
        return new Response<>(result);
    }
}
