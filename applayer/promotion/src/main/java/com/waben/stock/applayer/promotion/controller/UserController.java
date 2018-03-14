package com.waben.stock.applayer.promotion.controller;

import com.waben.stock.applayer.promotion.business.OrganizationBusiness;
import com.waben.stock.applayer.promotion.business.RoleBusiness;
import com.waben.stock.applayer.promotion.business.UserBusiness;
import com.waben.stock.applayer.promotion.util.SecurityAccount;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDetailDto;
import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.dto.organization.UserDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.RoleQuery;
import com.waben.stock.interfaces.pojo.query.organization.UserQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.manage.RoleVo;
import com.waben.stock.interfaces.vo.organization.OrganizationVo;
import com.waben.stock.interfaces.vo.organization.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
        OrganizationDto org = null;
        if(vo.getOrg()!=null) {
            org = CopyBeanUtils.copyBeanProperties(OrganizationDto.class,vo.getOrg(),false);
        }else{
        UserDto current = (UserDto) SecurityAccount.current().getSecurity();
            org = current.getOrg();
        }
        requestDto.setOrg(org);
        UserDto userDto = userBusiness.save(requestDto, org);
        UserVo userVo = CopyBeanUtils.copyBeanProperties(UserVo.class,userDto , false);
        return new Response<>(userVo);
    }

//    @RequestMapping("/save/{currentOrgId}")
//    @ResponseBody
//    public Response<UserVo> add(UserVo vo){
//        UserDto requestDto = CopyBeanUtils.copyBeanProperties(UserDto.class, vo, false);
//        requestDto.setOrg(CopyBeanUtils.copyBeanProperties(OrganizationDto.class,vo.getOrg(),false));
//        OrganizationDto organizationDto = new OrganizationDto();
//        organizationDto.setId(currentOrgId);
//        UserDto userDto = userBusiness.save(requestDto, organizationDto);
//        UserVo userVo = CopyBeanUtils.copyBeanProperties(UserVo.class,userDto , false);
//        return new Response<>(userVo);
//    }

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
        PageInfo<UserVo> response = new PageInfo<>(userVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        for (int i = 0; i < pageInfo.getContent().size(); i++) {
            OrganizationVo organizationVo = CopyBeanUtils.copyBeanProperties(
                    OrganizationVo.class, pageInfo.getContent().get(i).getOrg(), false);
            userVoContent.get(i).setOrg(organizationVo);
            Long role = pageInfo.getContent().get(i).getRole();
            if(role!=null) {
                RoleDto roleDto = roleBusiness.findById(role);
                userVoContent.get(i).setRoleName(roleDto.getName());
            }
        }
        return new Response<>(response);
    }
}
