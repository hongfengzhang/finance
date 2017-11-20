package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.manage.MenuService;
import com.waben.stock.applayer.operation.service.manage.StaffService;
import com.waben.stock.applayer.operation.warpper.auth.AccountCredentials;
import com.waben.stock.interfaces.dto.manage.MenuDto;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.StaffQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/17.
 * @desc
 */
@Service
public class MenuBusiness {

    @Autowired
    private MenuService menuService;

    public List<MenuDto> menus() {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();
        AccountCredentials accountCredentials = (AccountCredentials) token.getPrincipal();
        Long staff = accountCredentials.getStaff();
        Response<List<MenuDto>> menuResponse = menuService.menusByStaff(staff);
        if (menuResponse.getCode().equals("200")) {
            return menuResponse.getResult();
        }
        throw new ServiceException(menuResponse.getCode());
    }



}
