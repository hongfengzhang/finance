package com.waben.stock.datalayer.investors.service;

import com.waben.stock.datalayer.investors.entity.Investor;
import com.waben.stock.datalayer.investors.repository.InvestorDao;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
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
public class InvestorService {

    @Autowired
    private InvestorDao investorDao;

    @Transactional
    public Investor save(Investor investor) {
        return investorDao.create(investor);
    }

    public Page<Investor> pagesByQuery(final InvestorQuery query) {
        Pageable pageable = new PageRequest(query.getPage(), query.getSize());
        Page<Investor> pages = investorDao.page(new Specification<Investor>() {
            @Override
            public Predicate toPredicate(Root<Investor> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    criteriaBuilder) {
                if (!StringUtils.isEmpty(query.getUserName())) {
                    Predicate userNameQuery = criteriaBuilder.equal(root.get("userName").as(String.class), query
                            .getUserName());
                    criteriaQuery.where(criteriaBuilder.and(userNameQuery));
                }
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return pages;
    }

}
