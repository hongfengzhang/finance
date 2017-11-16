package com.waben.stock.datalayer.manage.repository.impl.jpa;

import java.util.Date;
import java.util.List;

import com.waben.stock.datalayer.manage.entity.Circulars;

/**
 * 通告 Jpa
 * 
 * @author luomengan
 *
 */
public interface CircularsRepository extends CustomJpaRepository<Circulars, Long> {

	List<Circulars> findByExpireTimeGreaterThan(Date date);

}
