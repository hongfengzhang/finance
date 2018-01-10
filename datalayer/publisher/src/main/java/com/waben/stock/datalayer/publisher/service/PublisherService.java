package com.waben.stock.datalayer.publisher.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.waben.stock.datalayer.publisher.entity.CapitalAccount;
import com.waben.stock.datalayer.publisher.entity.Publisher;
import com.waben.stock.datalayer.publisher.repository.CapitalAccountDao;
import com.waben.stock.datalayer.publisher.repository.PublisherDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.query.PublisherQuery;
import com.waben.stock.interfaces.util.ShareCodeUtil;
import com.waben.stock.interfaces.util.UniqueCodeGenerator;

/**
 * @author Created by yuyidi on 2017/11/12.
 * @desc
 */
@Service
public class PublisherService {

	@Autowired
	private PublisherDao publisherDao;

	@Autowired
	private CapitalAccountDao capitalAccountDao;

	public Publisher findById(Long id) {
		Publisher publisher = publisherDao.retrieve(id);
		if (publisher == null) {
			throw new ServiceException(ExceptionConstant.DATANOTFOUND_EXCEPTION);
		}
		return publisher;
	}

	@Transactional
	public Publisher register(String phone, String password, String promoter, String endType) {
		// 检查手机号
		Publisher check = publisherDao.retriveByPhone(phone);
		if (check != null) {
			throw new ServiceException(ExceptionConstant.PHONE_BEEN_REGISTERED_EXCEPTION);
		}
		// 保存发布策略人信息
		Publisher publisher = new Publisher();
		publisher.setSerialCode(UniqueCodeGenerator.generateSerialCode());
		publisher.setPhone(phone);
		publisher.setPassword(password);
		publisher.setCreateTime(new Date());
		publisher.setPromoter(promoter);
		publisher.setEndType(endType);
		publisherDao.create(publisher);
		publisher.setPromotionCode(ShareCodeUtil.encode(publisher.getId().intValue()));
		publisherDao.update(publisher);
		// 保存资金账号信息
		CapitalAccount account = new CapitalAccount();
		account.setBalance(new BigDecimal(0.00));
		account.setAvailableBalance(new BigDecimal(0.00));
		account.setFrozenCapital(new BigDecimal(0.00));
		account.setPublisherSerialCode(publisher.getSerialCode());
		account.setPublisherId(publisher.getId());
		account.setUpdateTime(new Date());
		capitalAccountDao.create(account);
		// 返回
		return publisher;
	}

	public Publisher findBySerialCode(String serialCode) {
		Publisher publisher = publisherDao.retriveBySerialCode(serialCode);
		if (publisher == null) {
			throw new ServiceException(ExceptionConstant.DATANOTFOUND_EXCEPTION);
		}
		return publisher;
	}

	public Publisher modifyPassword(String phone, String password) {
		// 检查手机号
		Publisher publisher = publisherDao.retriveByPhone(phone);
		if (publisher == null) {
			throw new ServiceException(ExceptionConstant.PHONE_ISNOT_REGISTERED_EXCEPTION);
		}
		// 更新密码
		publisher.setPassword(password);
		publisherDao.update(publisher);
		return publisher;
	}

	public Publisher findByPhone(String phone) {
		Publisher publisher = publisherDao.retriveByPhone(phone);
		if (publisher == null) {
			throw new ServiceException(ExceptionConstant.DATANOTFOUND_EXCEPTION);
		}
		return publisher;
	}

	public Integer promotionCount(Long id) {
		Publisher publisher = publisherDao.retrieve(id);
		if (publisher == null) {
			throw new ServiceException(ExceptionConstant.DATANOTFOUND_EXCEPTION);
		}

		return publisherDao.promotionCount(publisher.getPromotionCode());
	}

	public Page<Publisher> pagePromotionUser(Long id, int page, int size) {
		Publisher publisher = publisherDao.retrieve(id);
		if (publisher == null) {
			throw new ServiceException(ExceptionConstant.DATANOTFOUND_EXCEPTION);
		}
		return publisherDao.pageByPromoter(publisher.getPromotionCode(), page, size);
	}

	// 分页查询
	public Page<Publisher> pages(final PublisherQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<Publisher> pages = publisherDao.page(new Specification<Publisher>() {
			@Override
			public Predicate toPredicate(Root<Publisher> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				if(StringUtils.isEmpty(query.getPhone()) && StringUtils.isEmpty(query.getPhone()) && StringUtils.isEmpty(query.getPhone())){
					return criteriaQuery.getRestriction();
				}
				List<Predicate> predicatesList = new ArrayList<Predicate>();
				if (!StringUtils.isEmpty(query.getPhone())) {
					Predicate phoneQuery = criteriaBuilder.equal(root.get("phone").as(String.class), query
							.getPhone());
					predicatesList.add(phoneQuery);
				}
				if (!StringUtils.isEmpty(query.getPhone())) {
					Predicate promoterQuery = criteriaBuilder.equal(root.get("promoter").as(String.class), query
							.getPromoter());
					predicatesList.add(promoterQuery);
				}
				if (!StringUtils.isEmpty(query.getPhone())) {
					Predicate createTimeQuery = criteriaBuilder.equal(root.get("createTime").as(String.class), query
							.getCreateTime());
					predicatesList.add(createTimeQuery);
				}
				criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}

	public Publisher modiyHeadportrait(Long id, String headPortrait) {
		Publisher publisher = publisherDao.retrieve(id);
		if (publisher == null) {
			throw new ServiceException(ExceptionConstant.DATANOTFOUND_EXCEPTION);
		}
		if (headPortrait != null && !"".equals(headPortrait)) {
			publisher.setHeadPortrait(headPortrait);
			publisherDao.update(publisher);
		}
		return publisher;
	}

}
