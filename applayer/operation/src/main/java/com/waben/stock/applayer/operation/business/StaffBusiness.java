package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.manage.StaffService;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageAndSortQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StaffQuery;
import com.waben.stock.interfaces.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("staffFeignService")
    private StaffService staffService;

    public PageInfo<StaffDto> staffs(StaffQuery staffQuery) {
        Response<PageInfo<StaffDto>> response = staffService.pagesByQuery(staffQuery);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
