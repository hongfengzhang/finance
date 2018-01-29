package com.waben.stock.datalayer.manage.controller;

import com.waben.stock.datalayer.manage.entity.Banner;
import com.waben.stock.datalayer.manage.entity.Circulars;
import com.waben.stock.datalayer.manage.entity.Staff;
import com.waben.stock.datalayer.manage.service.CircularsService;
import com.waben.stock.interfaces.dto.manage.BannerDto;
import com.waben.stock.interfaces.dto.manage.CircularsDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CircularsQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.manage.CircularsInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import com.waben.stock.interfaces.util.PageToPageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 * @author yuyidi 2017-11-21 11:08:50
 * @class com.waben.stock.datalayer.manage.controller.CircularsController
 * @description 公告
 */
@RestController
@RequestMapping("/circulars")
public class CircularsController implements CircularsInterface {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CircularsService circularsService;

    @Override
    public Response<List<CircularsDto>> fetchCirculars(Boolean enable) {
        logger.info("是否获取是否可用的公告列表:{}", enable);
        List<Circulars> circulars = circularsService.findCirculars(enable);
        List<CircularsDto> circularsDtos = CopyBeanUtils.copyListBeanPropertiesToList(circulars, CircularsDto.class);
        return new Response<>(circularsDtos);
    }

    @Override
    public Response<PageInfo<CircularsDto>> pages(@RequestBody CircularsQuery query) {
        Page<Circulars> page = circularsService.pagesByQuery(query);
        PageInfo<CircularsDto> result = PageToPageInfo.pageToPageInfo(page, CircularsDto.class);
        return new Response<>(result);
    }

    @Override
    public Response<CircularsDto> fetchById(@PathVariable Long id) {
        Circulars circulars = circularsService.fetchById(id);
        CircularsDto circularsDto = CopyBeanUtils.copyBeanProperties(circulars, new CircularsDto(), false);
        return new Response<>(circularsDto);
    }

    @Override
    public Response<Integer> modify(@RequestBody CircularsDto circularsDto) {
        Circulars circulars = CopyBeanUtils.copyBeanProperties(Circulars.class, circularsDto, false);
        int result = circularsService.revision(circulars);
        return new Response<>(result);
    }

    @Override
    public void delete(@PathVariable Long id) {
        circularsService.delete(id);
    }
}
