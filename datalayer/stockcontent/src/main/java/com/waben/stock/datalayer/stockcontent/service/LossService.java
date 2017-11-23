package com.waben.stock.datalayer.stockcontent.service;

import com.waben.stock.datalayer.stockcontent.entity.Loss;
import com.waben.stock.datalayer.stockcontent.repository.LossDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/23.
 * @desc
 */
@Service
public class LossService {
    @Autowired
    private LossDao lossDao;


    @Transactional
    public Loss save(Loss loss) {
        return lossDao.create(loss);
    }

    public List<Loss> fetchLosses() {
        return lossDao.list();
    }
}
