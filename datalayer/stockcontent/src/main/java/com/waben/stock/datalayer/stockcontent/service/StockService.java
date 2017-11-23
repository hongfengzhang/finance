package com.waben.stock.datalayer.stockcontent.service;

import com.waben.stock.datalayer.stockcontent.entity.Stock;
import com.waben.stock.datalayer.stockcontent.repository.StockDao;
import com.waben.stock.interfaces.pojo.query.StockQuery;
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

/***
 * @author yuyidi 2017-11-22 10:08:52
 * @class com.waben.stock.datalayer.stockcontent.service.StockService
 * @description
 */
@Service
public class StockService {

    @Autowired
    private StockDao stockDao;

    @Transactional
    public Stock saveStock(Stock stock) {
        return stockDao.create(stock);
    }

    public Page<Stock> stocks(final StockQuery stockQuery) {
        Pageable pageable = new PageRequest(stockQuery.getPage(), stockQuery.getSize());
        Page<Stock> result = stockDao.page(new Specification<Stock>() {
            @Override
            public Predicate toPredicate(Root<Stock> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    criteriaBuilder) {
                if (!StringUtils.isEmpty(stockQuery.getName())) {
                    Predicate nameQuery = criteriaBuilder.equal(root.get("name").as(String.class), stockQuery.getName
                            ());
                    criteriaQuery.where(criteriaBuilder.and(nameQuery));
                } else if (!StringUtils.isEmpty(stockQuery.getCode())) {
                    Predicate codeQuery = criteriaBuilder.equal(root.get("code").as(String.class), stockQuery.getCode
                            ());
                    criteriaQuery.where(criteriaBuilder.and(codeQuery));
                }
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return result;
    }

}
