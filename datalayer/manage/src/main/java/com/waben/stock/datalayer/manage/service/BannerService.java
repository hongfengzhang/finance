package com.waben.stock.datalayer.manage.service;

import com.waben.stock.datalayer.manage.entity.Banner;
import com.waben.stock.datalayer.manage.entity.Permission;
import com.waben.stock.datalayer.manage.repository.BannerDao;
import com.waben.stock.interfaces.pojo.query.BannerQuery;
import com.waben.stock.interfaces.pojo.query.PermissionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    public Page<Banner> pagesByQuery(final BannerQuery query) {
        Pageable pageable = new PageRequest(query.getPage(), query.getSize());
        Page<Banner> pages = bannerDao.page(new Specification<Banner>() {
            @Override
            public Predicate toPredicate(Root<Banner> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    criteriaBuilder) {

                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return pages;
    }

}
