package com.waben.stock.datalayer.manage.repository;

import java.util.Date;
import java.util.List;

import com.waben.stock.datalayer.manage.entity.Circulars;

/**
 * 通告 Dao
 * 
 * @author luomengan
 *
 */
public interface CircularsDao extends BaseDao<Circulars, Long> {

	List<Circulars> findByExpireTimeGreaterThan(Date date);

}
