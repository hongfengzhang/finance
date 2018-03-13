package com.waben.stock.datalayer.manage.repository.impl.jpa;

import com.waben.stock.datalayer.manage.entity.Permission;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/12/11.
 * @desc
 */
public interface PermissionRepository extends CustomJpaRepository<Permission,Long> {
    List<Permission> findAllByVariety(Long variety);
}
