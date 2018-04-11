package com.waben.stock.datalayer.activity.service;

import com.waben.stock.datalayer.activity.entity.Activity;
import com.waben.stock.datalayer.activity.repository.ActivityDao;
import com.waben.stock.interfaces.dto.activity.ActivityDto;
import com.waben.stock.interfaces.exception.DataNotFoundException;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
	public ActivityDto saveActivity(Activity a){
		ad.saveActivity(a);
		return CopyBeanUtils.copyBeanProperties(ActivityDto.class, a, false);
	}
	
	
	public List<ActivityDto> getActivityList(int pageno,Integer pagesize){
		if(pagesize == null){
			PageAndSortQuery pq = new PageAndSortQuery();
			pagesize = pq.getSize();
		}
		List<Activity> li = ad.getActivityList(pageno,pagesize);
		List<ActivityDto> atolist = new ArrayList<>();
		if(li != null){
			for(Activity a : li){
				ActivityDto ad  = CopyBeanUtils.copyBeanProperties(ActivityDto.class, a, false);
				atolist.add(ad);
			}
		}
		return atolist;
	}
	
	@Transactional
	public void setValid(long activityId){
		Activity a = ad.getActivity(activityId);
		a.setIsvalid(!a.isIsvalid());
	}
	
	public ActivityDto getActivityById(long activityId){
		Activity activity = ad.getActivity(activityId);
		if (activity == null) {
			throw new DataNotFoundException("数据找不到");
		}
		return CopyBeanUtils.copyBeanProperties(ActivityDto.class,activity , false);
	}

	public ActivityDto getActivityByLocation(String location) {
		return CopyBeanUtils.copyBeanProperties(ActivityDto.class, ad.getActivityByLocation(location), false);
	}
}
