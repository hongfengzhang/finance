package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.PermissionBusiness;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PermissionQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.message.PermissionVo;
import com.waben.stock.interfaces.vo.message.StaffVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/12/11.
 * @desc
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionBusiness permissionBusiness;

    @RequestMapping("/index")
    public String stock() {
        return "manage/permission/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<PermissionVo>> pages(PermissionQuery query) {
        PageInfo<PermissionDto> pageInfo = permissionBusiness.pages(query);
        List<PermissionVo> permissionVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), PermissionVo.class);
        PageInfo<PermissionVo> response = new PageInfo<>(permissionVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }
}
