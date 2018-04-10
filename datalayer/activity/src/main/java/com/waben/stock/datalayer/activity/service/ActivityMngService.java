package com.waben.stock.datalayer.activity.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.activity.entity.Activity;
import com.waben.stock.datalayer.activity.repository.ActivityDao;
import com.waben.stock.interfaces.dto.activity.ActivityDto;
import com.waben.stock.interfaces.util.CopyBeanUtils;

/**
 * 活动管理服务
 * 
 * @author guowei 2018/4/10
 *
 */

@Service
public class ActivityMngService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ActivityDao ad;
	
	@Transactional
	public ActivityDto addActivity(Activity a){
		ad.saveActivity(a);
		return CopyBeanUtils.copyBeanProperties(ActivityDto.class, a, false);
	}
}
