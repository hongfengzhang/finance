package com.waben.stock.datalayer.message.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.waben.stock.datalayer.message.entity.Messaging;
import com.waben.stock.datalayer.message.repository.MessagingDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.enums.MessageType;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.query.MessagingQuery;

/**
 * 
 * @author Created by hujian on 2018年1月4日
 */
@Service
public class MessagingService {

	@Autowired
	private MessagingDao messagingDao;
	
	public Messaging save(Messaging messaging){
		return messagingDao.create(messaging);
	}
	
	public Long remove(Long messagingId){
		messagingDao.delete(messagingId);
		return messagingId;
	}
	
	public Messaging revision(Messaging messaging){
		return messagingDao.update(messaging);
	}
	
	public Messaging findById(Long messagingId){
		Messaging messaging = messagingDao.retrieve(messagingId);
		if(messaging == null){
			throw new ServiceException(ExceptionConstant.DATANOTFOUND_EXCEPTION);
		}
		return messaging;
	}
	
	public Page<Messaging> pages(final MessagingQuery messagingQuery){
		Pageable pageable = new PageRequest(messagingQuery.getPage(), messagingQuery.getSize());
		Page<Messaging> pages = messagingDao.page(new Specification<Messaging>() {
			@Override
			public Predicate toPredicate(Root<Messaging> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				if(StringUtils.isEmpty(messagingQuery.getTitle()) && StringUtils.isEmpty(messagingQuery.getMessageType()) 
						&& StringUtils.isEmpty(messagingQuery.getBeginTime())){
					return criteriaQuery.getRestriction();
				}
				List<Predicate> predicatesList = new ArrayList<Predicate>();
				if (!StringUtils.isEmpty(messagingQuery.getTitle())) {
					Predicate titleQuery = criteriaBuilder.like(root.get("title").as(String.class), "%"+messagingQuery
							.getTitle()+"%");
					predicatesList.add(criteriaBuilder.and(titleQuery));
				}
				if(!StringUtils.isEmpty(messagingQuery.getMessageType())){
					Predicate stateQuery = criteriaBuilder.equal(root.get("type").as(MessageType.class), MessageType.getByType(messagingQuery
							.getMessageType()));
					predicatesList.add(criteriaBuilder.and(stateQuery));
				}
				if(!StringUtils.isEmpty(messagingQuery.getBeginTime()) && !StringUtils.isEmpty(messagingQuery.getEndTime())){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date beginTime = null;
					Date endTime = null;
					try {
						beginTime = sdf.parse(messagingQuery.getBeginTime());
						endTime = sdf.parse(messagingQuery.getEndTime());
					} catch (ParseException e) {
						throw new ServiceException(ExceptionConstant.DATETIME_ERROR);
					}
					Predicate createTimeQuery = criteriaBuilder.between(root.<Date>get("createTime").as(Date.class),beginTime,endTime);
					predicatesList.add(criteriaBuilder.and(createTimeQuery));
				}
				criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
				criteriaQuery.orderBy(criteriaBuilder.desc(root.<Date>get("createTime").as(Date.class)));
				return criteriaQuery.getRestriction();
			}
		}, pageable);
		return pages;
	}
	
	/**
	 * 查询当前用户未产生回执的消息集合
	 * @param recipient
	 * @return
	 */
	public List<Messaging> findNotProduceReceiptMessage(String recipient){
		return messagingDao.retrieveNotProduceReceiptAllByRecipient(recipient);
	}
	
}
