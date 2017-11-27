package com.waben.stock.datalayer.stockcontent.repository;

import com.waben.stock.datalayer.stockcontent.entity.StrategyType;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/23.
 * @desc
 */
public interface StraegyTypeDao extends BaseDao<StrategyType,Long>{

    List<StrategyType> retrieveWithEnable();

}
