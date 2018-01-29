package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.StaffBusiness;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import com.waben.stock.interfaces.dto.stockcontent.LossDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StaffQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.vo.investor.InvestorVo;
import com.waben.stock.interfaces.vo.message.RoleVo;
import com.waben.stock.interfaces.vo.message.StaffVo;
import com.waben.stock.interfaces.vo.stockcontent.LossVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/19.
 * @desc
 */
@Controller
@RequestMapping("/staff")
public class StaffController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StaffBusiness staffBusiness;

    @RequestMapping("/index")
    public String user() {
        return "manage/staff/index";
    }

    @RequestMapping("/pages")
    @ResponseBody
    public Response<PageInfo<StaffVo>> pages(StaffQuery staffQuery) {
        PageInfo<StaffDto> pageInfo = staffBusiness.staffs(staffQuery);
        List<StaffVo> staffVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), StaffVo.class);
        PageInfo<StaffVo> response = new PageInfo<>(staffVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Long id, ModelMap map){
        StaffDto staffDto = staffBusiness.fetchById(id);
        StaffVo staffVo = CopyBeanUtils.copyBeanProperties(StaffVo.class, staffDto, false);
        staffVo.setRoleVo(CopyBeanUtils.copyBeanProperties(RoleVo.class, staffDto.getRoleDto(), false));
        map.addAttribute("staff", staffVo);
        return "manage/staff/view";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id,ModelMap map){
        StaffDto staffDto = staffBusiness.findById(id);
        StaffVo staffVo = CopyBeanUtils.copyBeanProperties(StaffVo.class, staffDto, false);
        map.addAttribute("staff", staffVo);
        return "manage/staff/edit";
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Response<Integer> modify(StaffVo vo){
        StaffDto requestDto = CopyBeanUtils.copyBeanProperties(StaffDto.class, vo, false);
        Integer result = staffBusiness.revision(requestDto);
        return new Response<>(result);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Response<Integer> delete(Long id){
        staffBusiness.delete(id);
        return new Response<>(1);
    }
}
