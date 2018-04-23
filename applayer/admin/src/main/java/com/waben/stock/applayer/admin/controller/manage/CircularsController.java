package com.waben.stock.applayer.admin.controller.manage;

import com.waben.stock.applayer.admin.business.manage.CircularsBusiness;
import com.waben.stock.interfaces.dto.manage.CircularsDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CircularsQuery;
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

/**
 * @Author zengzhiwei
 * @Create 2018/4/23 14:19
 */
@Controller
@RequestMapping("/circulars")
@Api("公告")
public class CircularsController {

    @Autowired
    private CircularsBusiness circularsBusiness;

    @RequestMapping("/index")
    public String stock() {
        return "manage/circulars/index";
    }

    @GetMapping("/pages")
    @ResponseBody
    @ApiImplicitParam(paramType = "query", dataType = "CircularsQuery", name = "query", value = "查询对象", required = true)
    @ApiOperation(value = "公告分页")
    public Response<PageInfo<CircularsDto>> pages(CircularsQuery query) {
        PageInfo<CircularsDto> response = circularsBusiness.pages(query);
        return new Response<>(response);
    }

    @RequestMapping("/view/{id}")
    @ApiImplicitParam(paramType = "query", dataType = "CircularsQuery", name = "query", value = "查询对象", required = true)
    @ApiOperation(value = "公告分页")
    public String view(@PathVariable Long id, ModelMap map){
        CircularsDto circularsDto = circularsBusiness.fetchById(id);
        map.addAttribute("circulars", circularsDto);
        return "manage/circulars/view";
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    @ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "公告id", required = true)
    @ApiOperation(value = "公告删除")
    public Response<Integer> delete(@PathVariable Long id){
        circularsBusiness.delete(id);
        return new Response<>(1);
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id,ModelMap map){
        CircularsDto circularsDto = circularsBusiness.fetchById(id);
        map.addAttribute("circulars", circularsDto);
        return "manage/circulars/edit";
    }

    @RequestMapping("/modify")
    @ResponseBody
    @ApiImplicitParam(paramType = "query", dataType = "CircularsDto", name = "circularsDto", value = "公告对象", required = true)
    @ApiOperation(value = "公告修改")
    public Response<Integer> modify(CircularsDto circularsDto){
        Integer result = circularsBusiness.revision(circularsDto);
        return new Response<>(result);
    }

    @RequestMapping("/add")
    public String add() {
        return "manage/circulars/add";
    }

    @RequestMapping("/save")
    @ResponseBody
    @ApiImplicitParam(paramType = "query", dataType = "CircularsDto", name = "circularsDto", value = "公告对象", required = true)
    @ApiOperation(value = "公告添加")
    public Response<CircularsDto> add(CircularsDto circularsDto){
        CircularsDto response = circularsBusiness.save(circularsDto);
        return new Response<>(response);
    }
}
