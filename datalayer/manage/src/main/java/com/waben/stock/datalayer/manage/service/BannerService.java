package com.waben.stock.datalayer.manage.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.manage.entity.Banner;
import com.waben.stock.datalayer.manage.entity.BannerForward;
import com.waben.stock.datalayer.manage.repository.BannerDao;
import com.waben.stock.interfaces.enums.BannerForwardCategory;
import com.waben.stock.interfaces.pojo.query.BannerQuery;

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
			public Predicate toPredicate(Root<Banner> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				if (query.getCategory() != null) {
					Join<Banner, BannerForward> join = root.join("bannerForward", JoinType.LEFT);
					criteriaQuery.where(criteriaBuilder.equal(join.get("category").as(BannerForwardCategory.class),
							query.getCategory()));
				}
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

}
