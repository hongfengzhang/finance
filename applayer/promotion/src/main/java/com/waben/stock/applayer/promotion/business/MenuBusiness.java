package com.waben.stock.applayer.promotion.business;

import com.waben.stock.applayer.promotion.reference.manage.MenuReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.MenuDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Created by yuyidi on 2018/3/14.
 * @desc
 */
@Service
public class MenuBusiness {

    @Autowired
    @Qualifier("menuReference")
    private MenuReference menuReference;

    public List<MenuDto> menus(Long role) {
        Response<List<MenuDto>> response = menuReference.menusByRole(role);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

}
