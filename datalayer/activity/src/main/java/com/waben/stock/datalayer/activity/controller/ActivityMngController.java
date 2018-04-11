package com.waben.stock.datalayer.activity.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.waben.stock.datalayer.activity.service.ActivityMngService;
import com.waben.stock.interfaces.dto.activity.ActivityDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.activity.ActivityMngInterface;

/**
 * 
 * @author guowei 2018/4/11
 *
 */
public class ActivityMngController implements ActivityMngInterface{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ActivityMngService ams; 
	
	@Override
	public Response<ActivityDto> saveActivity(ActivityDto adto) {
		ActivityDto ad = ams.saveActivity(adto);
		
		return new Response<>(ad);
	}

	
	@Override
	public Response<List<ActivityDto>> getActivityList(int pageno, Integer pagesize) {
		return new Response<>(ams.getActivityList(pageno, pagesize));
	}

	@Override
	public Response<Void> setValid(long activityId) {
		ams.setValid(activityId);
		
		return new Response<Void>();
	}


	@Override
	public Response<ActivityDto> getActivity(long activityId) {
		return new Response<>(ams.getActivityById(activityId));
	}
	
	

	
}
