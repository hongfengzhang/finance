package com.waben.stock.datalayer.organization.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.organization.entity.BenefitConfig;
import com.waben.stock.datalayer.organization.entity.Organization;
import com.waben.stock.datalayer.organization.entity.OrganizationPublisher;
import com.waben.stock.datalayer.organization.repository.BenefitConfigDao;
import com.waben.stock.datalayer.organization.repository.OrganizationDao;
import com.waben.stock.datalayer.organization.repository.OrganizationPublisherDao;

/**
 * 机构结算 Service
 * 
 * @author luomengan
 *
 */
@Service
public class OrganizationSettlementService {

	@Autowired
	private OrganizationPublisherDao orgPublisherDao;

	@Autowired
	private OrganizationDao orgDao;

	@Autowired
	private BenefitConfigDao benefitConfigDao;

	@Transactional
	public void strategySettlement(Long publisherId, Long buyRecordId, Long strategyTypeId, BigDecimal serviceFee,
			BigDecimal deferredFee) {
		List<Organization> orgTreeList = getPublisherOrgTreeList(publisherId);
		if (orgTreeList != null) {
//			// 获取服务费配置列表
//			List<BenefitConfig> benefitConfig = new ArrayList<>();
//			for (int i = orgTreeList.size() - 2; i >= 0; i--) {
//				Organization org = orgTreeList.get(i);
//				benefitConfigDao.retrieveByOrgAndTypeAndResourceTypeAndResourceId(org, type, resourceType, resourceId);
//			}

			
		}
	}

	public void stockoptionSettlement(Long publisherId, Long stockOptionTradeId, Long cycleId,
			BigDecimal rightMoneyProfit) {
		List<Organization> orgTreeList = getPublisherOrgTreeList(publisherId);
		if (orgTreeList != null) {

		}
	}

	private List<Organization> getPublisherOrgTreeList(Long publisherId) {
		OrganizationPublisher orgPublisher = orgPublisherDao.retrieveByPublisherId(publisherId);
		if (orgPublisher != null) {
			Organization org = orgDao.retrieveByCode(orgPublisher.getOrgCode());
			if (org != null) {
				List<Organization> orgList = new ArrayList<>();
				orgList.add(org);
				while (org.getParent() != null) {
					orgList.add(org.getParent());
					org = org.getParent();
				}
				if (orgList.get(orgList.size() - 1).getLevel() == 1 && orgList.size() > 1) {
					return orgList;
				}
			}
		}
		return null;
	}

}
