package com.waben.stock.applayer.promotion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waben.stock.applayer.promotion.business.RoleBusiness;
import com.waben.stock.applayer.promotion.business.UserBusiness;
import com.waben.stock.applayer.promotion.util.SecurityAccount;
import com.waben.stock.applayer.promotion.warpper.auth.AccountCredentials;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.dto.organization.UserDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.organization.UserQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.organization.OrganizationVo;
import com.waben.stock.interfaces.vo.organization.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserBusiness userBusiness;

    @Autowired
    private RoleBusiness roleBusiness;

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

    @RequestMapping("/pages")
    @ResponseBody
    public Response<PageInfo<UserVo>> pages(@RequestBody UserQuery userQuery) {
        UserDto userDto = (UserDto) SecurityAccount.current().getSecurity();
        userQuery.setOrganization(userDto.getOrg().getId());
        PageInfo<UserDto> pageInfo = userBusiness.pages(userQuery);
        List<UserVo> userVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), UserVo.class);
        for (int i = 0; i < userVoContent.size(); i++) {
            OrganizationVo organizationDto = CopyBeanUtils.copyBeanProperties(
                    OrganizationVo.class, userVoContent.get(i).getOrg(), false);
            userVoContent.get(i).setOrg(organizationDto);
        }
        PageInfo<UserVo> response = new PageInfo<>(userVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());

        for (UserVo userVo : response.getContent()) {
            if(userVo.getRole()!=null) {
                RoleDto roleDto = roleBusiness.findById(userVo.getRole());
                userVo.setRoleName(roleDto.getName());
            }
        }
        return new Response<>(response);
    }
    
    @RequestMapping(value = "/getCurrent", method = RequestMethod.GET)
    @ResponseBody
	public Response<UserDto> getCurrent() {
    	AccountCredentials details = SecurityAccount.current();
		return new Response<>((UserDto)details.getSecurity());
	}
    
}
