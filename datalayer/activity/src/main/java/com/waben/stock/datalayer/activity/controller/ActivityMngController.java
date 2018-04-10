package com.waben.stock.datalayer.activity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.waben.stock.datalayer.activity.entity.Activity;
import com.waben.stock.datalayer.activity.service.ActivityMngService;
import com.waben.stock.interfaces.dto.activity.ActivityDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.activity.ActivityMngInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;

public class ActivityMngController implements ActivityMngInterface{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ActivityMngService ams; 
	
	@Override
	public Response<ActivityDto> addActivity(ActivityDto adto) {
		Activity a = CopyBeanUtils.copyBeanProperties(Activity.class, adto, false);
		ActivityDto ad = ams.addActivity(a);
		
		return new Response<>(ad);
	}

}
