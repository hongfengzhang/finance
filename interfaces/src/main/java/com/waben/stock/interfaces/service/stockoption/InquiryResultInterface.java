package com.waben.stock.interfaces.service.stockoption;

import com.waben.stock.interfaces.dto.stockcontent.LossDto;
import com.waben.stock.interfaces.dto.stockoption.InquiryResultDto;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface InquiryResultInterface {
    @RequestMapping(value = "/add", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<InquiryResultDto> add(@RequestBody InquiryResultDto inquiryResultDto);
}
