package com.waben.stock.datalayer.manage.service;

import com.waben.stock.datalayer.manage.entity.Staff;
import com.waben.stock.datalayer.manage.repository.StaffDao;
import com.waben.stock.interfaces.exception.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Created by yuyidi on 2017/11/15.
 * @desc
 */
@Service
public class StaffService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StaffDao staffDao;

    public Staff fetchByUserName(String username) {
        Staff result = staffDao.findByUserName(username);
        if (result == null) {
            throw new DataNotFoundException("用户不存在");
        }
        return result;
    }
}
