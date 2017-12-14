package com.waben.stock.applayer.operation.business;

import com.waben.stock.applayer.operation.service.manage.BannerService;
import com.waben.stock.interfaces.dto.manage.BannerDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BannerQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BannerBusiness {

    @Autowired
    @Qualifier("bannerFeignService")
    private BannerService bannerService;


    public PageInfo<BannerDto> pages(BannerQuery bannerQuery) {
        Response<PageInfo<BannerDto>> response = bannerService.pages(bannerQuery);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }
}
