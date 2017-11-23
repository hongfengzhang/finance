package com.waben.stock.datalayer.stockcontent.service;

import com.waben.stock.datalayer.stockcontent.entity.AmountValue;
import com.waben.stock.datalayer.stockcontent.repository.AmountValueDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/23.
 * @desc
 */
@Service
public class AmountValueService {

    @Autowired
    private AmountValueDao amountValueDao;

    @Transactional
    public AmountValue save(AmountValue amountValue) {
        return amountValueDao.create(amountValue);
    }

    public List<AmountValue> fetchAmountValues() {
        return amountValueDao.list();
    }
}
