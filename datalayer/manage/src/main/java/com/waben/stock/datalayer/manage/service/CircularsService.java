package com.waben.stock.datalayer.manage.service;

import com.waben.stock.datalayer.manage.entity.Circulars;
import com.waben.stock.datalayer.manage.repository.CircularsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 通告 Service
 *
 * @author luomengan
 */
@Service
public class CircularsService {

    @Autowired
    private CircularsDao circularsDao;

    /***
     * @author yuyidi 2017-11-21 10:48:14
     * @method findCirculars
     * @param enable
     * @return java.util.List<com.waben.stock.datalayer.manage.entity.Circulars>
     * @description 获取是否有效的公告列表  若enable为空，则获取所有的未过期且有效的公告列表
     */
    public List<Circulars> findCirculars(Boolean enable) {
        if (enable) {
            return circularsDao.retrieveCirculars(enable);
        }
        return circularsDao.retrieveCircularsWithInExpireTime(new Date());
    }

<<<<<<< HEAD
=======
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
>>>>>>> 5091503fca7ec8bf69793ce6aebaded483393d07

}
