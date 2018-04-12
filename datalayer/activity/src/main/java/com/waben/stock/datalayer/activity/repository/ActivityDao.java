package com.waben.stock.datalayer.activity.repository;

import java.util.List;

import com.waben.stock.datalayer.activity.entity.Activity;

public interface ActivityDao  {
	
	void saveActivity(Activity a);
	
	List<Activity> getActivityList(int pageno,int pagesize);
	
	Activity getActivity(long activityId);
	Activity getActivityByLocation(String location);
}
