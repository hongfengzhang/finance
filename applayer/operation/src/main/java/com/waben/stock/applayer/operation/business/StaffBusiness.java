package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.manage.StaffService;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StaffQuery;
import com.waben.stock.interfaces.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author Created by yuyidi on 2017/11/19.
 * @desc
 */
@Service
public class StaffBusiness {

    @Autowired
    private StaffService staffService;

    public PageInfo<StaffDto> staffs(StaffQuery staffQuery) {
        Response<PageInfo<StaffDto>> response = staffService.pagesByQuery(staffQuery);
        if (response.getCode().equals("200")) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }
}
