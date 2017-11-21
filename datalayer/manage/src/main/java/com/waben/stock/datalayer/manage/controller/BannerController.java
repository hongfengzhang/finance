package com.waben.stock.datalayer.manage.controller;

import com.waben.stock.datalayer.manage.entity.Banner;
import com.waben.stock.datalayer.manage.service.BannerService;
import com.waben.stock.interfaces.dto.manage.BannerDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.manage.BannerInterface;
import com.waben.stock.interfaces.util.CopyBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 * @author yuyidi 2017-11-21 10:59:28
 * @class com.waben.stock.datalayer.manage.controller.BannerController
 * @description 系统轮播图
 */
@RestController
@RequestMapping("/banner")
public class BannerController implements BannerInterface {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BannerService bannerService;

    @Override
    public Response<List<BannerDto>> fetchBanners(Boolean enable) {
        logger.info("是否获取是否可用的轮播图列表:{}", enable);
        List<Banner> banners = bannerService.findBanners(enable);
        List<BannerDto> bannerDtos = CopyBeanUtils.copyListBeanPropertiesToList(banners, BannerDto.class);
        return new Response<>(bannerDtos);
    }
}
