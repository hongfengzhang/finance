package com.waben.stock.datalayer.publisher.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.publisher.entity.CapitalAccount;
import com.waben.stock.datalayer.publisher.entity.Publisher;
import com.waben.stock.datalayer.publisher.repository.CapitalAccountDao;
import com.waben.stock.datalayer.publisher.repository.PublisherDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.util.SerialCodeGenerator;

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
	public Publisher register(String phone, String password) {
		// 检查手机号
		Publisher check = publisherDao.retriveByPhone(phone);
		if (check != null) {
			throw new ServiceException(ExceptionConstant.PHONE_BEEN_REGISTERED_EXCEPTION);
		}
		// 保存发布策略人信息
		Publisher publisher = new Publisher();
		publisher.setSerialCode(SerialCodeGenerator.generate());
		publisher.setPhone(phone);
		publisher.setPassword(password);
		publisher.setCreateTime(new Date());
		publisherDao.create(publisher);
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

}
