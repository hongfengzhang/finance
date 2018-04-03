package com.waben.stock.datalayer.organization.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.organization.entity.Organization;
import com.waben.stock.datalayer.organization.entity.OrganizationPublisher;
import com.waben.stock.datalayer.organization.repository.OrganizationDao;
import com.waben.stock.datalayer.organization.repository.OrganizationPublisherDao;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.exception.ServiceException;

/**
 * 机构推广的发布人 Service
 *
 * @author luomengan
 */
@Service
public class OrganizationPublisherService {

	@Autowired
	private OrganizationPublisherDao dao;

	@Autowired
	private OrganizationDao orgDao;

	public OrganizationPublisher addOrgPublisher(String orgCode, Long publisherId) {
		if (orgCode == null || "".equals(orgCode.trim())) {
			return null;
		}
		Organization org = orgDao.retrieveByCode(orgCode);
		if (org == null) {
			throw new ServiceException(ExceptionConstant.ORGCODE_NOTEXIST_EXCEPTION);
		}
		OrganizationPublisher orgPublisher = dao.retrieveByPublisherId(publisherId);
		if (orgPublisher != null) {
			throw new ServiceException(ExceptionConstant.ORGPUBLISHER_EXIST_EXCEPTION);
		}
		orgPublisher = new OrganizationPublisher();
		orgPublisher.setOrgCode(orgCode);
		orgPublisher.setPublisherId(publisherId);
		orgPublisher.setCreateTime(new Date());
		return dao.create(orgPublisher);
	}

}
