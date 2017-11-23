package com.waben.stock.datalayer.stockcontent.service;

import com.waben.stock.datalayer.stockcontent.entity.AmountValue;
import com.waben.stock.datalayer.stockcontent.entity.Loss;
import com.waben.stock.datalayer.stockcontent.entity.StraegyType;
import com.waben.stock.datalayer.stockcontent.repository.StraegyTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/23.
 * @desc
 */
@Service
public class StraegyTypeService {


    @Autowired
    private StraegyTypeDao straegyTypeDao;

    /***
    * @author yuyidi 2017-11-23 16:06:42
    * @method save
     * @param straegyType 策略类型
     * @param amountValues 市值集合
     * @param losses 止损比例集合
    * @return com.waben.stock.datalayer.stockcontent.entity.StraegyType
    * @description 添加股票策略类型，绑定策略类型市值与止损比例集合
    */
    public StraegyType save(StraegyType straegyType, List<AmountValue> amountValues, List<Loss> losses) {
        StraegyType model = straegyType;
        model.addAmountValues(amountValues);
        model.addLosses(losses);
        return straegyTypeDao.create(model);
    }

    public List<StraegyType> lists(Boolean enable) {
        if (enable) {
            return straegyTypeDao.retrieveWithEnable();
        }
        return straegyTypeDao.list();
    }
}
