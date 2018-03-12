package com.waben.stock.datalayer.organization.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.organization.entity.Organization;
import com.waben.stock.datalayer.organization.entity.OrganizationAccount;
import com.waben.stock.datalayer.organization.entity.OrganizationAccountFlow;
import com.waben.stock.datalayer.organization.repository.OrganizationAccountDao;
import com.waben.stock.datalayer.organization.repository.OrganizationAccountFlowDao;
import com.waben.stock.interfaces.enums.OrganizationAccountFlowType;
import com.waben.stock.interfaces.enums.ResourceType;
import com.waben.stock.interfaces.util.UniqueCodeGenerator;

/**
 * 机构账户 Service
 *
 * @author luomengan
 */
@Service
public class OrganizationAccountService {

	@Autowired
	private OrganizationAccountDao organizationAccountDao;

	@Autowired
	private OrganizationAccountFlowDao flowDao;

	public OrganizationAccount getOrganizationAccountInfo(Long id) {
		return organizationAccountDao.retrieve(id);
	}

	@Transactional
	public OrganizationAccount addOrganizationAccount(OrganizationAccount organizationAccount) {
		return organizationAccountDao.create(organizationAccount);
	}

	@Transactional
	public OrganizationAccount modifyOrganizationAccount(OrganizationAccount organizationAccount) {
		return organizationAccountDao.update(organizationAccount);
	}

	@Transactional
	public void deleteOrganizationAccount(Long id) {
		organizationAccountDao.delete(id);
	}

	@Transactional
	public void deleteOrganizationAccounts(String ids) {
		if (ids != null) {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				if (!"".equals(id.trim())) {
					organizationAccountDao.delete(Long.parseLong(id.trim()));
				}
			}
		}
	}

	public Page<OrganizationAccount> organizationAccounts(int page, int limit) {
		return organizationAccountDao.page(page, limit);
	}

	public List<OrganizationAccount> list() {
		return organizationAccountDao.list();
	}

	public synchronized OrganizationAccount benefit(Organization org, BigDecimal amount,
			OrganizationAccountFlowType flowType, ResourceType resourceType, Long resourceId) {
		Date date = new Date();
		OrganizationAccount account = null;
		if (org != null) {
			account = organizationAccountDao.retrieveByOrg(org);
			if (account == null) {
				account = new OrganizationAccount();
				account.setAvailableBalance(new BigDecimal("0"));
				account.setBalance(new BigDecimal("0"));
				account.setFrozenCapital(new BigDecimal("0"));
				account.setOrg(org);
				account.setUpdateTime(new Date());
				organizationAccountDao.create(account);
			}
			increaseAmount(account, amount, date);
		}
		// 产生流水
		OrganizationAccountFlow flow = new OrganizationAccountFlow();
		flow.setAmount(amount);
		flow.setFlowNo(UniqueCodeGenerator.generateFlowNo());
		flow.setOccurrenceTime(date);
		flow.setOrg(org);
		flow.setResourceType(resourceType);
		flow.setResourceId(resourceId);
		flow.setType(flowType);
		flow.setRemark(flowType.getType());
		flowDao.create(flow);
		return account;
	}

	/**
	 * 账户增加金额
	 * 
	 * @param account
	 *            资金账户
	 * @param amount
	 *            金额
	 */
	private synchronized void increaseAmount(OrganizationAccount account, BigDecimal amount, Date date) {
		account.setBalance(account.getBalance().add(amount));
		account.setAvailableBalance(account.getAvailableBalance().add(amount));
		account.setUpdateTime(date);
		organizationAccountDao.update(account);
	}

}
