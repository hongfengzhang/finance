package com.waben.stock.datalayer.manage.service;

import com.waben.stock.datalayer.manage.entity.Role;
import com.waben.stock.datalayer.manage.repository.RoleDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.exception.ServiceException;
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

    public Role findById(Long id) {
        Role role = roleDao.retrieve(id);
        if (role == null) {
            throw new ServiceException(ExceptionConstant.ROLE_NOT_FOUND_EXCEPTION);
        }
        return role;
    }
}
