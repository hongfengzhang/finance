package com.waben.stock.datalayer.manage.controller;

import com.waben.stock.datalayer.manage.entity.Circulars;
import com.waben.stock.datalayer.manage.service.CircularsService;
import com.waben.stock.interfaces.dto.manage.CircularsDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.CircularsInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
import java.util.List;
=======
import com.waben.stock.datalayer.manage.service.CircularsService;
import com.waben.stock.interfaces.dto.manage.CircularsDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.manage.CircularsInterface;
>>>>>>> 5091503fca7ec8bf69793ce6aebaded483393d07

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

<<<<<<< HEAD
    @Override
    public Response<List<CircularsDto>> fetchCirculars(Boolean enable) {
        logger.info("是否获取是否可用的公告列表:{}", enable);
        List<Circulars> circulars = circularsService.findCirculars(enable);
        List<CircularsDto> circularsDtos = CopyBeanUtils.copyListBeanPropertiesToList(circulars, CircularsDto.class);
        return new Response<>(circularsDtos);
    }
=======
	@Override
	public Response<List<CircularsDto>> getByEnable(boolean enable) {
		return new Response<>(circularsService.getByEnable(enable));
	}
>>>>>>> 5091503fca7ec8bf69793ce6aebaded483393d07
}
