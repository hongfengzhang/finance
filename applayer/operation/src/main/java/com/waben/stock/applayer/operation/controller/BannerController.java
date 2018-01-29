package com.waben.stock.applayer.operation.controller;

import com.waben.stock.applayer.operation.business.BannerBusiness;
import com.waben.stock.interfaces.dto.manage.BannerDto;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.dto.manage.RoleDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BannerQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PermissionQuery;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.vo.message.BannerVo;
import com.waben.stock.interfaces.vo.message.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerBusiness bannerBusiness;

    @RequestMapping("/index")
    public String index() {
        return "manage/banner/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    public Response<PageInfo<BannerVo>> pages(BannerQuery query) {
        PageInfo<BannerDto> pageInfo = bannerBusiness.pages(query);
        List<BannerVo> bannerVoContent = CopyBeanUtils.copyListBeanPropertiesToList(pageInfo.getContent(), BannerVo.class);
        PageInfo<BannerVo> response = new PageInfo<>(bannerVoContent, pageInfo.getTotalPages(), pageInfo.getLast(), pageInfo.getTotalElements(), pageInfo.getSize(), pageInfo.getNumber(), pageInfo.getFrist());
        return new Response<>(response);
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Long id, ModelMap map){
        BannerDto bannerDto = bannerBusiness.fetchById(id);
        BannerVo bannerVo = CopyBeanUtils.copyBeanProperties(BannerVo.class, bannerDto, false);
        map.addAttribute("banner", bannerVo);
        return "manage/banner/view";
    }
    @RequestMapping("/delete")
    @ResponseBody
    public Response<Integer> delete(Long id){
        bannerBusiness.delete(id);
        return new Response<>(1);
    }
}
