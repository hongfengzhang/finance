package com.waben.stock.datalayer.manage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.manage.entity.Circulars;
import com.waben.stock.datalayer.manage.repository.CircularsDao;
import com.waben.stock.interfaces.dto.manage.CircularsDto;

/**
 * 通告 Service
 * 
 * @author luomengan
 *
 */
@Service
public class CircularsService {

	@Autowired
	private CircularsDao circularsDao;

	public List<CircularsDto> getByEnable(boolean enable) {
		List<CircularsDto> result = new ArrayList<>();
		if (enable) {
			List<Circulars> entityList = circularsDao.findByExpireTimeGreaterThan(new Date());
			if (entityList != null && entityList.size() > 0) {
				for (Circulars entity : entityList) {
					result.add(entity.copy());
				}
			}
		} else {
			// TODO
		}
		return result;
	}

}
