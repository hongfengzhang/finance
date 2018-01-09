package com.waben.stock.applayer.operation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waben.stock.applayer.operation.business.MessagingBusiness;
import com.waben.stock.interfaces.dto.message.MessagingDto;
import com.waben.stock.interfaces.enums.MessageType;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.MessagingQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.MessagingVo;

/**
 * 
 * @author Created by hujian on 2018年1月4日
 */
@Controller
@RequestMapping("messaging")
public class MessagingController {

	@Autowired
	private MessagingBusiness messagingBusiness;
	
	@RequestMapping("/index")
    public String index(ModelMap map) {
		map.addAttribute("messageType", MessageType.values());
        return "message/manage/index";
    }
	
	@GetMapping("/pages")
    @ResponseBody
	public Response<PageInfo<MessagingVo>> pages(MessagingQuery messagingQuery){
		PageInfo<MessagingDto> pages = messagingBusiness.pages(messagingQuery);
		List<MessagingVo> MessagingVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pages.getContent(), MessagingVo.class); 
		PageInfo<MessagingVo> result = new PageInfo<>(MessagingVoContent, pages.getTotalPages(), pages.getLast(), pages.getTotalElements(), pages.getSize(), pages.getNumber(), pages.getFrist());
		return new Response<>(result);
	}
	
}
