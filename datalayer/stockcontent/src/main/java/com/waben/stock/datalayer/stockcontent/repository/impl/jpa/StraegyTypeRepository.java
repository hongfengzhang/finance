package com.waben.stock.datalayer.stockcontent.repository.impl.jpa;

import com.waben.stock.datalayer.stockcontent.entity.StraegyType;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/23.
 * @desc
 */
public interface StraegyTypeRepository extends CustomJpaRepository<StraegyType, Long> {

    List<StraegyType> findAllByState(Boolean state);

}
