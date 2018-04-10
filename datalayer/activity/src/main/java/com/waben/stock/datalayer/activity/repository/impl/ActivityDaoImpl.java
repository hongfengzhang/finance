package com.waben.stock.datalayer.activity.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.waben.stock.datalayer.activity.entity.Activity;
import com.waben.stock.datalayer.activity.repository.ActivityDao;
import com.waben.stock.datalayer.activity.repository.jpa.ActivityRespository;


@Repository
public class ActivityDaoImpl implements ActivityDao {

	@Autowired
	private ActivityRespository ar;
	
	@Override
	public void saveActivity(Activity a) {
		ar.save(a);
	}

}
