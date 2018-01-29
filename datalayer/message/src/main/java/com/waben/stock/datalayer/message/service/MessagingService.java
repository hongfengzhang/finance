package com.waben.stock.datalayer.message.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.waben.stock.datalayer.message.entity.MessageReceipt;
import com.waben.stock.datalayer.message.entity.Messaging;
import com.waben.stock.datalayer.message.repository.MessageReceiptDao;
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

	@Autowired
	private MessageReceiptDao messageReceiptDao;

	public Messaging save(Messaging messaging) {
		return messagingDao.create(messaging);
	}

	public Long remove(Long messagingId) {
		messagingDao.delete(messagingId);
		return messagingId;
	}

	public Messaging revision(Messaging messaging) {
		return messagingDao.update(messaging);
	}

	public Messaging findById(Long messagingId) {
		Messaging messaging = messagingDao.retrieve(messagingId);
		if (messaging == null) {
			throw new ServiceException(ExceptionConstant.DATANOTFOUND_EXCEPTION);
		}
		return messaging;
	}

	private Page<Messaging> pagesByReceipt(final MessagingQuery query) {
		Pageable pageable = new PageRequest(query.getPage(), query.getSize());
		Page<MessageReceipt> pages = messageReceiptDao.page(new Specification<MessageReceipt>() {
			@Override
			public Predicate toPredicate(Root<MessageReceipt> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				Join<MessageReceipt, Messaging> join = root.join("message", JoinType.LEFT);
				List<Predicate> predicateList = new ArrayList<>();
				if (query.getPublisherId() != null && query.getPublisherId() > 0) {
					predicateList.add(criteriaBuilder.equal(root.get("recipient").as(String.class),
							String.valueOf(query.getPublisherId())));
				}
				if (query.getIsOutside() != null) {
					predicateList
							.add(criteriaBuilder.equal(join.get("isOutside").as(Boolean.class), query.getIsOutside()));
				}
				if (query.getHasRead() != null) {
					predicateList.add(criteriaBuilder.equal(root.get("state").as(Boolean.class), query.getHasRead()));
				}
				if (predicateList.size() > 0) {
					criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
				}
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("state").as(Boolean.class)),
						criteriaBuilder.desc(join.get("createTime").as(Long.class)));
				criteriaQuery.orderBy(criteriaBuilder.desc(join.get("createTime").as(Long.class)));
				return criteriaQuery.getRestriction();
			}
		}, pageable);

		List<Messaging> content = new ArrayList<>();
		if (pages.getContent() != null && pages.getContent().size() > 0) {
			for (MessageReceipt receipt : pages.getContent()) {
				Messaging messaging = receipt.getMessage();
				messaging.setHasRead(receipt.getState());
				content.add(messaging);
			}
		}
		return new PageImpl<>(content, pageable, pages.getTotalElements());
	}

	public Page<Messaging> pages(final MessagingQuery messagingQuery) {
		Pageable pageable = new PageRequest(messagingQuery.getPage(), messagingQuery.getSize());
		if (messagingQuery.getPublisherId() != null) {
			return pagesByReceipt(messagingQuery);
		}
		Page<Messaging> pages = messagingDao.page(new Specification<Messaging>() {
			@Override
			public Predicate toPredicate(Root<Messaging> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicatesList = new ArrayList<Predicate>();
				if (!StringUtils.isEmpty(messagingQuery.getTitle())) {
					Predicate titleQuery = criteriaBuilder.like(root.get("title").as(String.class),
							"%" + messagingQuery.getTitle() + "%");
					predicatesList.add(criteriaBuilder.and(titleQuery));
				}
				if (!StringUtils.isEmpty(messagingQuery.getMessageType())) {
					Predicate stateQuery = criteriaBuilder.equal(root.get("type").as(MessageType.class),
							MessageType.getByType(messagingQuery.getMessageType()));
					predicatesList.add(criteriaBuilder.and(stateQuery));
				}
				if (messagingQuery.getBeginTime() != null && messagingQuery.getEndTime() != null) {
					Predicate createTimeQuery = criteriaBuilder.between(root.<Date>get("createTime").as(Date.class),
							messagingQuery.getBeginTime(), messagingQuery.getEndTime());
					predicatesList.add(criteriaBuilder.and(createTimeQuery));
				}
				if (messagingQuery.getIsOutside() != null) {
					Predicate stateQuery = criteriaBuilder.equal(root.get("isOutside").as(Boolean.class),
							messagingQuery.getIsOutside());
					predicatesList.add(stateQuery);
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
	 * 
	 * @param recipient
	 * @return
	 */
	public List<Messaging> findNotProduceReceiptMessage(String recipient) {
		return messagingDao.retrieveNotProduceReceiptAllByRecipient(recipient);
	}

	public Messaging readMessage(String recipient, Long id) {
		Messaging messaging = messagingDao.retrieve(id);
		if (messaging == null) {
			return null;
		}
		MessageReceipt receipt = messageReceiptDao.findByMessageAndRecipient(messaging, recipient);
		if (receipt == null) {
			return messaging;
		} else {
			receipt.setState(true);
			messageReceiptDao.update(receipt);
		}
		messaging.setHasRead(true);
		return messaging;
	}

}
