package com.waben.stock.datalayer.stockcontent.service;

import com.waben.stock.datalayer.stockcontent.entity.AmountValue;
import com.waben.stock.datalayer.stockcontent.entity.Loss;
import com.waben.stock.datalayer.stockcontent.entity.StrategyType;
import com.waben.stock.datalayer.stockcontent.repository.StrategyTypeDao;
import com.waben.stock.interfaces.pojo.query.StrategyTypeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/23.
 * @desc
 */
@Service
public class StrategyTypeService {


    @Autowired
    private StrategyTypeDao strategyTypeDao;

    /***
     * @author yuyidi 2017-11-23 16:06:42
     * @method save
     * @param straegyType 策略类型
     * @param amountValues 市值集合
     * @param losses 止损比例集合
     * @return com.waben.stock.datalayer.stockcontent.entity.StrategyType
     * @description 添加股票策略类型，绑定策略类型市值与止损比例集合
     */
    public StrategyType save(StrategyType straegyType, List<AmountValue> amountValues, List<Loss> losses) {
        StrategyType model = straegyType;
        model.addAmountValues(amountValues);
        model.addLosses(losses);
        return strategyTypeDao.create(model);
    }

    public List<StrategyType> lists(Boolean enable) {
        if (enable) {
            return strategyTypeDao.retrieveWithEnable();
        }
        return strategyTypeDao.list();
    }

    public Page<StrategyType> pages(final StrategyTypeQuery query) {
        Pageable pageable = new PageRequest(query.getPage(), query.getSize());
        Page<StrategyType> pages = strategyTypeDao.page(new Specification<StrategyType>() {
            @Override
            public Predicate toPredicate(Root<StrategyType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder
                    criteriaBuilder) {
                if (!StringUtils.isEmpty(query.getType())) {
                    Predicate typeQuery = criteriaBuilder.equal(root.get("type").as(String.class), query
                            .getType());
                    criteriaQuery.where(criteriaBuilder.and(typeQuery));
                }
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return pages;
    }
}
