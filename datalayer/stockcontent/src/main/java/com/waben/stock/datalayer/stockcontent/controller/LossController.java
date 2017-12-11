package com.waben.stock.datalayer.stockcontent.controller;

import com.waben.stock.datalayer.stockcontent.entity.Loss;
import com.waben.stock.datalayer.stockcontent.entity.Stock;
import com.waben.stock.datalayer.stockcontent.service.LossService;
import com.waben.stock.interfaces.dto.stockcontent.LossDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.LossQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.stockcontent.LossInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loss")
public class LossController implements LossInterface{

    @Autowired
    private LossService lossService;

    @Override
    public Response<PageInfo<LossDto>> pagesByQuery(@RequestBody LossQuery lossQuery) {
        Page<Loss> losses = lossService.pages(lossQuery);
        PageInfo<LossDto> result = new PageInfo<>(losses, LossDto.class);
        return new Response<>(result);
    }
}
