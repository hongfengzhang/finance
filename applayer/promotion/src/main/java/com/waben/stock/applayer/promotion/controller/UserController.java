package com.waben.stock.applayer.promotion.controller;

import com.waben.stock.applayer.promotion.business.UserBusiness;
import com.waben.stock.applayer.promotion.util.SecurityAccount;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.dto.organization.UserDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.manage.RoleVo;
import com.waben.stock.interfaces.vo.organization.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserBusiness userBusiness;

    @RequestMapping("/save")
    @ResponseBody
    public Response<UserVo> add(UserVo vo){
        UserDto requestDto = CopyBeanUtils.copyBeanProperties(UserDto.class, vo, false);
        requestDto.setOrg(CopyBeanUtils.copyBeanProperties(OrganizationDto.class,vo.getOrg(),false));
        UserDto current = (UserDto) SecurityAccount.current().getSecurity();
        UserDto userDto = userBusiness.save(requestDto, current.getOrg());
        UserVo userVo = CopyBeanUtils.copyBeanProperties(UserVo.class,userDto , false);
        return new Response<>(userVo);
    }

    @Deprecated
    @RequestMapping("/{id}/role")
    @ResponseBody
    public Response<UserVo> bindRole(@PathVariable Long id, Long[] roleIds){
        UserDto userDto = userBusiness.saveUserRole(id,roleIds);
        UserVo userVo = CopyBeanUtils.copyBeanProperties(UserVo.class,userDto , false);
        return new Response<>(userVo);
    }

}
