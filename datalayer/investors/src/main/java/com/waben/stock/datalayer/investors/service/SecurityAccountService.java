package com.waben.stock.datalayer.investors.service;

import com.waben.stock.datalayer.investors.entity.Investor;
import com.waben.stock.datalayer.investors.entity.SecurityAccount;
import com.waben.stock.datalayer.investors.repository.SecurityAccountDao;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.SecurityAccountQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@Service
public class SecurityAccountService {

    @Autowired
    private SecurityAccountDao securityAccountDao;

    @Transactional
    public SecurityAccount save(SecurityAccount securityAccount) {
        return securityAccountDao.create(securityAccount);
    }

    public Page<SecurityAccount> pagesByQuery(final SecurityAccountQuery query) {
        Pageable pageable = new PageRequest(query.getPage(), query.getSize());
        Page<SecurityAccount> pages = securityAccountDao.page(new Specification<SecurityAccount>() {
            @Override
            public Predicate toPredicate(Root<SecurityAccount> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    criteriaBuilder) {
                if (!StringUtils.isEmpty(query.getAccount())) {
                    Predicate userNameQuery = criteriaBuilder.equal(root.get("account").as(String.class), query
                            .getAccount());
                    criteriaQuery.where(criteriaBuilder.and(userNameQuery));
                }
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return pages;
    }
}
