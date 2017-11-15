package com.waben.stock.datalayer.manage.repository.impl.jpa;

import com.waben.stock.datalayer.manage.entity.Staff;

/**
 * @author Created by yuyidi on 2017/11/15.
 * @desc
 */
public interface StaffRepository extends CustomJpaRepository<Staff, Long> {

    Staff findByUserName(String userName);
}
