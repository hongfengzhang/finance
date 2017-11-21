package com.waben.stock.datalayer.publisher.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.publisher.entity.CapitalAccount;
import com.waben.stock.datalayer.publisher.entity.Publisher;
import com.waben.stock.datalayer.publisher.repository.CapitalAccountDao;
import com.waben.stock.datalayer.publisher.repository.PublisherDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.PublisherCapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.PublisherDto;
import com.waben.stock.interfaces.exception.ServiceException;

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

	public PublisherDto findById(Long id) {
		Publisher publisher = publisherDao.retrieve(id);
		if (publisher == null) {
			throw new RuntimeException();
		}
		return publisher.copy();
	}

	@Transactional
	public PublisherCapitalAccountDto register(String phone, String password) {
		// 检查手机号
		Publisher check = publisherDao.findByPhone(phone);
		if (check != null) {
			throw new ServiceException(ExceptionConstant.PHONE_BEEN_REGISTERED_EXCEPTION);
		}
		// 保存发布策略人信息
		Publisher publisher = new Publisher();
		publisher.setSerialCode(UUID.randomUUID().toString());
		publisher.setPhone(phone);
		publisher.setPassword(password);
		publisher.setCreateTime(new Date());
		publisherDao.create(publisher);
		// 保存资金账号信息
		CapitalAccount account = new CapitalAccount();
		account.setBalance(new BigDecimal(0.00));
		account.setPublisherSerialCode(publisher.getSerialCode());
		account.setUpdateTime(new Date());
		capitalAccountDao.create(account);
		// 返回
		return new PublisherCapitalAccountDto(publisher.copy(), account.copy());
	}

	public PublisherCapitalAccountDto findBySerialCode(String serialCode) {
		Publisher publisher = publisherDao.findBySerialCode(serialCode);
		CapitalAccount account = capitalAccountDao.findByPublisherSerialCode(serialCode);
		return new PublisherCapitalAccountDto(publisher.copy(), account.copy());
	}

	public PublisherCapitalAccountDto modifyPassword(String phone, String password) {
		// 检查手机号
		Publisher publisher = publisherDao.findByPhone(phone);
		if (publisher == null) {
			throw new ServiceException(ExceptionConstant.PHONE_ISNOT_REGISTERED_EXCEPTION);
		}
		// 更新密码
		publisher.setPassword(password);
		publisherDao.update(publisher);
		CapitalAccount account = capitalAccountDao.findByPublisherSerialCode(publisher.getSerialCode());
		return new PublisherCapitalAccountDto(publisher.copy(), account.copy());
	}

	public PublisherDto findByPhone(String phone) {
		Publisher publisher = publisherDao.findByPhone(phone);
		return publisher != null ? publisher.copy() : null;
	}

	public void modifyPaymentPassword(String serialCode, String paymentPassword) {
		CapitalAccount account = capitalAccountDao.findByPublisherSerialCode(serialCode);
		account.setPaymentPassword(paymentPassword);
		capitalAccountDao.update(account);
	}

}
