package com.waben.stock.applayer.admin.controller.manage;

import com.waben.stock.applayer.admin.business.manage.BannerBusiness;
import com.waben.stock.interfaces.dto.manage.BannerDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BannerQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/banner")
@Api("轮播图")
public class BannerController {

    @Autowired
    private BannerBusiness bannerBusiness;

    @RequestMapping("/index")
    public String index() {
        return "manage/banner/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    @ApiImplicitParam(paramType = "query", dataType = "BannerQuery", name = "query", value = "查询对象", required = true)
    @ApiOperation(value = "轮播图分页")
    public Response<PageInfo<BannerDto>> pages(BannerQuery query) {
        PageInfo<BannerDto> pageInfo = bannerBusiness.pages(query);
        return new Response<>(pageInfo);
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Long id, ModelMap map){
        BannerDto bannerDto = bannerBusiness.fetchById(id);
        map.addAttribute("banner", bannerDto);
        return "manage/banner/view";
    }
    @RequestMapping("/delete/{id}")
    @ResponseBody
    @ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "轮播图id", required = true)
    @ApiOperation(value = "轮播图删除")
    public Response<Integer> delete(@PathVariable Long id){
        bannerBusiness.delete(id);
        return new Response<>(1);
    }

    @RequestMapping("/add")
    public String add() {
        return "manage/banner/add";
    }

    @RequestMapping("/save")
    @ResponseBody
    @ApiImplicitParam(paramType = "query", dataType = "BannerDto", name = "bannerDto", value = "轮播图对象", required = true)
    @ApiOperation(value = "轮播图添加")
    public Response<BannerDto> add(BannerDto bannerDto){
        BannerDto response = bannerBusiness.save(bannerDto);
        return new Response<>(response);
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id,ModelMap map){
        BannerDto bannerDto = bannerBusiness.fetchById(id);
        map.addAttribute("banner", bannerDto);
        return "manage/banner/edit";
    }

    @RequestMapping("/modify")
    @ResponseBody
    @ApiImplicitParam(paramType = "query", dataType = "BannerDto", name = "bannerDto", value = "轮播图对象", required = true)
    @ApiOperation(value = "轮播图修改")
    public Response<BannerDto> modify(BannerDto bannerDto){
        BannerDto response = bannerBusiness.revision(bannerDto);
        return new Response<>(response);
    }
}
