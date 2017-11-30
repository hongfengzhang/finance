package com.waben.stock.datalayer.publisher.repository;

import com.waben.stock.datalayer.publisher.entity.Publisher;

/**
 * @author Created by yuyidi on 2017/11/12.
 * @desc
 */
public interface PublisherDao extends BaseDao<Publisher, Long> {

	Publisher retriveByPhone(String phone);

	Publisher retriveBySerialCode(String serialCode);

}
