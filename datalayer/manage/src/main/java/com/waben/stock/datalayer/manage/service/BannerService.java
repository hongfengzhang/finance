package com.waben.stock.datalayer.manage.service;

import com.waben.stock.datalayer.manage.entity.Banner;
import com.waben.stock.datalayer.manage.repository.BannerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播 Service
 *
 * @author luomengan
 */
@Service
public class BannerService {

    @Autowired
    private BannerDao bannerDao;

    /***
     * @author yuyidi 2017-11-21 11:15:44
     * @method findBanners
     * @param enable
     * @return java.util.List<com.waben.stock.datalayer.manage.entity.Banner>
     * @description
     */
    public List<Banner> findBanners(Boolean enable) {
        if (enable) {
            return bannerDao.retrieveBanners(enable);
        }
        return bannerDao.retrieveBannersOrderByCreateTime();
    }

}
