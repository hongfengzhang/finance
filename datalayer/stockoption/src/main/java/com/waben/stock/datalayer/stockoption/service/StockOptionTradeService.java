package com.waben.stock.datalayer.stockoption.service;

import com.waben.stock.datalayer.stockoption.entity.StockOptionTrade;
import com.waben.stock.datalayer.stockoption.repository.StockOptionTradeDao;
import com.waben.stock.interfaces.pojo.query.StockOptionTradeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StockOptionTradeService {

    @Autowired
    private StockOptionTradeDao stockOptionTradeDao;

    public Page<StockOptionTrade> pagesByQuery(final StockOptionTradeQuery query) {
        Pageable pageable = new PageRequest(query.getPage(), query.getSize());
        Page<StockOptionTrade> pages = stockOptionTradeDao.page(new Specification<StockOptionTrade>() {
            @Override
            public Predicate toPredicate(Root<StockOptionTrade> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    criteriaBuilder) {
                List<Predicate> predicatesList = new ArrayList();
                if (query.getBeginTime() != null) {
                    predicatesList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("buyingTime").as(Date.class),
                            query.getBeginTime()));
                }
                if (query.getEndTime() != null) {
                    predicatesList.add(criteriaBuilder.lessThan(root.get("buyingTime").as(Date.class),
                            query.getEndTime()));
                }
                if(!StringUtils.isEmpty(query.getPublisherPhone())) {
                    Predicate publisherPhoneQuery = criteriaBuilder.equal(root.get("publisherPhone").as(Long.class), query
                            .getPublisherPhone());
                    predicatesList.add(publisherPhoneQuery);
                }
                if(!StringUtils.isEmpty(query.getApplyNo())) {
                    Predicate applyNoQuery = criteriaBuilder.equal(root.get("applyNo").as(String.class), query
                            .getApplyNo());
                    predicatesList.add(applyNoQuery);
                }
                if(!StringUtils.isEmpty(query.getState())) {
                    Predicate stateQuery = criteriaBuilder.equal(root.get("state").as(Integer.class), query
                            .getState());
                    predicatesList.add(stateQuery);
                }
                criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return pages;
    }
}
