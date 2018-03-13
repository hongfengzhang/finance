package com.waben.stock.interfaces.service.organization;

import com.waben.stock.interfaces.dto.organization.UserDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Created by yuyidi on 2018/3/12.
 * @desc
 */
public interface UserInterface {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Response<UserDto> fetchById(@PathVariable("id") Long id);

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    Response<PageInfo<UserDto>> users(@RequestParam("page") int page, @RequestParam("limit") int limit);

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    Response<List<UserDto>> list();

    /******************************** 后台管理 **********************************/

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Response<UserDto> addition(@RequestBody UserDto user);

    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Response<UserDto> modification(@RequestBody UserDto user);

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    Response<Long> delete(@PathVariable("id") Long id);

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    Response<Boolean> deletes(@RequestParam("ids") String ids);

    @RequestMapping(value = "/adminList", method = RequestMethod.GET)
    Response<List<UserDto>> adminList();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    Response<UserDto> fetchByUserName(@RequestParam("userName") String userName);

    @RequestMapping(value = "/user/{user}/role/{role}", method = RequestMethod.PUT)
    Response<UserDto> bindRole(@PathVariable("user") Long user, @PathVariable("role") Long role);
}
