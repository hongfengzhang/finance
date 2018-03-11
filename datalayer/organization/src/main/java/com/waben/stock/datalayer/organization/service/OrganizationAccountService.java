package com.waben.stock.datalayer.organization.service;

import java.util.List;

import com.waben.stock.interfaces.dto.organization.OrganizationAccountFlowDto;
import com.waben.stock.interfaces.pojo.query.organization.OrganizationAccountFlowQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waben.stock.datalayer.organization.entity.OrganizationAccount;
import com.waben.stock.datalayer.organization.repository.OrganizationAccountDao;

/**
 * 机构账户 Service
 *
 * @author luomengan
 */
@Service
public class OrganizationAccountService {

    @Autowired
    private OrganizationAccountDao organizationAccountDao;

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

}
