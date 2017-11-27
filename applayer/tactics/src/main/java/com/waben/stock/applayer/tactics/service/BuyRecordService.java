package com.waben.stock.applayer.tactics.service;

import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;

import com.waben.stock.applayer.tactics.wrapper.FeignConfiguration;
import com.waben.stock.interfaces.service.buyrecord.BuyRecordInterface;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 点买记录 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "buyrecord/buyrecord", path = "buyRecord", configuration = FeignConfiguration.class)
@Primary
public interface BuyRecordService {


    @RequestMapping(value = "/addBuyRecord", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<BuyRecordDto> addBuyRecord(@RequestBody BuyRecordDto buyRecordDto);


}
