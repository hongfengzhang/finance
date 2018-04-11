package com.waben.stock.datalayer.activity.repository;

import java.util.List;

import com.waben.stock.datalayer.activity.entity.Activity;

public interface ActivityDao  {
	
	public void saveActivity(Activity a);
	
	public List<Activity> getActivityList(int pageno,int pagesize);
	
	public Activity getActivity(long activityId);
	
}
