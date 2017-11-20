package com.waben.stock.datalayer.manage.service;

import com.waben.stock.datalayer.manage.entity.Role;
import com.waben.stock.datalayer.manage.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public List<Role> fetchRoleByStaff(Long staffId) {
        List<Role> roles = roleDao.findByStaffId(staffId);
        return roles;
    }
}
