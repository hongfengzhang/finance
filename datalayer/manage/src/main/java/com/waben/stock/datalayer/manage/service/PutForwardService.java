package com.waben.stock.datalayer.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.manage.entity.PutForward;
import com.waben.stock.datalayer.manage.repository.PutForwardDao;

@Service
public class PutForwardService {

	@Autowired
	private PutForwardDao putDao;
	
	public List<PutForward> findAll(){
		return putDao.list();
	}
	
	public Page<PutForward> pages(){
		return putDao.page(0, Integer.MAX_VALUE);
	}
	
	public PutForward saveAndModify(PutForward put){
		return putDao.create(put);
	}
	
}
