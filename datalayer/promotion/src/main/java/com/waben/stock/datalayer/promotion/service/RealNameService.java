package com.waben.stock.datalayer.promotion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.promotion.entity.RealName;
import com.waben.stock.datalayer.promotion.repository.RealNameDao;

/**
 * 实名信息 Service
 * 
 * @author luomengan
 *
 */
@Service
public class RealNameService {

	@Autowired
	private RealNameDao realNameDao;

	public RealName getRealNameInfo(Long id) {
		return realNameDao.retrieve(id);
	}

	@Transactional
	public RealName addRealName(RealName realName) {
		return realNameDao.create(realName);
	}

	@Transactional
	public RealName modifyRealName(RealName realName) {
		return realNameDao.update(realName);
	}

	@Transactional
	public void deleteRealName(Long id) {
		realNameDao.delete(id);
	}
	
	@Transactional
	public void deleteRealNames(String ids) {
		if(ids != null) {
			String[] idArr= ids.split(",");
			for(String id : idArr) {
				if(!"".equals(id.trim())) {
					realNameDao.delete(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<RealName> realNames(int page, int limit) {
		return realNameDao.page(page, limit);
	}
	
	public List<RealName> list() {
		return realNameDao.list();
	}

}
