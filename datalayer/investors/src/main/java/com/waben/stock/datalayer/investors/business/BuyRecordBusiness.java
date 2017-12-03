package com.waben.stock.datalayer.investors.business;

import com.waben.stock.datalayer.investors.entity.Investor;
import com.waben.stock.datalayer.investors.reference.BuyRecordReference;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.stock.stockjy.SecuritiesStockEntrust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Created by yuyidi on 2017/12/2.
 * @desc
 */
@Service
public class BuyRecordBusiness {

    @Autowired
    private BuyRecordReference buyRecordReference;

    /***
     * @author yuyidi 2017-12-02 16:25:47
     * @method entrust
     * @param entrust
     * @return com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto
     * @description 点买交易订单委托买入成功后向点买服务发起买入锁定请求
     */
    public BuyRecordDto entrust(Investor investor, SecuritiesStockEntrust securitiesStockEntrust, String entrust) {
        securitiesStockEntrust.setEntrustNumber(entrust);
        Response<BuyRecordDto> response = buyRecordReference.buyLock(investor.getId(), securitiesStockEntrust.getBuyRecordId());
        if ("200".equals(response.getCode())) {
            BuyRecordDto result = response.getResult();
            if (result.getState().equals(BuyRecordState.BUYLOCK)) {
                //点买交易买入成功，将当前已委托买入的点买订单放入内存中轮询

                return response.getResult();
            }
        }
        throw new ServiceException(response.getCode());
    }
}
