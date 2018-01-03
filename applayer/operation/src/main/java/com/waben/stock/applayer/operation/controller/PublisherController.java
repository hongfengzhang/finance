package com.waben.stock.applayer.operation.controller;


import com.waben.stock.applayer.operation.business.CapitalFlowBusiness;
import com.waben.stock.applayer.operation.business.PublisherBusiness;
import com.waben.stock.interfaces.dto.publisher.CapitalFlowDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CapitalFlowQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PublisherQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/publisher")
public class PublisherController {

    @Autowired
    private PublisherBusiness publisherBusiness;
    
    @Autowired
    private CapitalFlowBusiness capitalFlowBusiness;

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
    
    @RequestMapping("/view/{id}")
    public String view(@PathVariable Long id,ModelMap map){
    	map.addAttribute("publisher", publisherBusiness.fetchById(id));
    	return "publisher/manage/view";
    }
    
    @RequestMapping("/capitalflow/pages")
    @ResponseBody
    public Response<PageInfo<CapitalFlowDto>> fetchCapitalFlowPages(CapitalFlowQuery capitalFlowQuery){
    	PageInfo<CapitalFlowDto> response = capitalFlowBusiness.pages(capitalFlowQuery);
        return new Response<>(response);
    }
}
