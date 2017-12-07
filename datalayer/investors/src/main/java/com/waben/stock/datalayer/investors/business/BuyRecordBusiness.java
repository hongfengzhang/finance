package com.waben.stock.datalayer.investors.business;

import com.waben.stock.datalayer.investors.entity.Investor;
import com.waben.stock.datalayer.investors.reference.BuyRecordReference;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Created by yuyidi on 2017/12/2.
 * @desc
 */
@Service
public class BuyRecordBusiness {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("buyRecordFeignReference")
    private BuyRecordReference buyRecordReference;

    /***
     * @author yuyidi 2017-12-02 16:25:47
     * @method entrust
     * @param entrust
     * @return com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto
     * @description 点买交易订单委托买入成功后向点买服务发起买入锁定请求
     */
    public BuyRecordDto buyRecordApplyBuyIn(Investor investor, SecuritiesStockEntrust securitiesStockEntrust, String
            entrust) {
//        securitiesStockEntrust.setEntrustNumber(entrust);
        Response<BuyRecordDto> response = buyRecordReference.buyLock(investor.getId(), securitiesStockEntrust
                .getBuyRecordId(), entrust);
        if ("200".equals(response.getCode())) {
            BuyRecordDto result = response.getResult();
            result.setDelegateNumber(entrust);
            if (result.getState().equals(BuyRecordState.BUYLOCK)) {
                logger.info("点买记录买入锁定成功:{}", result.getTradeNo());
                return response.getResult();
            }
        }
        throw new ServiceException(response.getCode());
    }

    public BuyRecordDto entrustApplySellOut(Investor investor, SecuritiesStockEntrust securitiesStockEntrust, String
            entrust) {
//        securitiesStockEntrust.setEntrustNumber(entrust);
        Response<BuyRecordDto> response = buyRecordReference.sellLock(investor.getId(), securitiesStockEntrust
                .getBuyRecordId(), entrust);
        if ("200".equals(response.getCode())) {
            BuyRecordDto result = response.getResult();
            result.setDelegateNumber(entrust);
            if (result.getState().equals(BuyRecordState.SELLLOCK)) {
                return response.getResult();
            }
        }
        throw new ServiceException(response.getCode());
    }
}
