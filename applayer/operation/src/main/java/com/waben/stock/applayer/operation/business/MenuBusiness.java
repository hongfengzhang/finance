package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.manage.MenuService;
import com.waben.stock.applayer.operation.util.SecurityAccount;
import com.waben.stock.applayer.operation.warpper.auth.AccountCredentials;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.MenuDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/17.
 * @desc
 */
@Service
public class MenuBusiness {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("menuFeignService")
    private MenuService menuService;

    public List<MenuDto> menus() {
        AccountCredentials current = SecurityAccount.current();
        Long role = current.getRole();
        Response<List<MenuDto>> response = menuService.menusByRole(role);
        logger.info("获取菜单信息:{}", JacksonUtil.encode(response));
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }


}
