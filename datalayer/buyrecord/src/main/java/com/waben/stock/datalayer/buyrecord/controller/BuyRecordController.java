package com.waben.stock.datalayer.buyrecord.controller;

import com.waben.stock.datalayer.buyrecord.entity.BuyRecord;
import com.waben.stock.datalayer.buyrecord.service.BuyRecordService;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.buyrecord.BuyRecordInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.PageToPageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 点买记录 Controller
 *
 * @author luomengan
 */
@RestController
@RequestMapping("/buyRecord")
public class BuyRecordController implements BuyRecordInterface {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BuyRecordService buyRecordService;

    @Override
    public Response<BuyRecordDto> addBuyRecord(@RequestBody BuyRecordDto buyRecordDto) {
        BuyRecord buyRecord = CopyBeanUtils.copyBeanProperties(BuyRecord.class, buyRecordDto, false);
        return new Response<>(
                CopyBeanUtils.copyBeanProperties(BuyRecordDto.class, buyRecordService.save(buyRecord), false));
    }

    @Override
    public Response<BuyRecordDto> buyInto(Long id, String delegateNumber, BigDecimal buyingPrice,
                                          Integer numberOfStrand) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Response<PageInfo<BuyRecordDto>> pagesByQuery(BuyRecordQuery buyRecordQuery) {
        Page<BuyRecord> page = buyRecordService.pagesByQuery(buyRecordQuery);
        PageInfo<BuyRecordDto> result = PageToPageInfo.pageToPageInfo(page, BuyRecordDto.class);
        return new Response<>(result);
    }

    @RequestMapping("/topic")
    public Response<Void> topicMessage(String security ,String message) {
        buyRecordService.messageTopic(security,message);
        return new Response<>();
    }

}
