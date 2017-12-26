package com.waben.stock.applayer.operation.controller;


import com.waben.stock.applayer.operation.business.PublisherBusiness;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PublisherQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/publisher")
public class PublisherController {

    @Autowired
    private PublisherBusiness publisherBusiness;

    @RequestMapping("/index")
    public String index() {
        return "publisher/manage/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<PublisherDto>> pages(PublisherQuery publisherQuery) {
        PageInfo<PublisherDto> response = publisherBusiness.pages(publisherQuery);
        return new Response<>(response);
    }
    
    @RequestMapping("/view")
    public String view(){
    	return "publisher/manage/view";
    }
}
