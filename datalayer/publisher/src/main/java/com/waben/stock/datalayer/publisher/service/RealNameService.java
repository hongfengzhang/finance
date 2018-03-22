package com.waben.stock.datalayer.publisher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.publisher.entity.RealName;
import com.waben.stock.datalayer.publisher.repository.RealNameDao;

/**
 * 实名认证 Service
 * 
 * @author luomengan
 *
 */
@Service
public class RealNameService {

	@Autowired
	private RealNameDao realNameDao;

	public RealName save(RealName realName) {
		return realNameDao.create(realName);
	}

}
