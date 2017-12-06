package com.waben.stock.interfaces.pojo.stock.stockjy;

import com.waben.stock.interfaces.exception.ServiceException;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/12/5.
 * @desc
 */
public class StockResponseHander {

    protected  <T> List<T> handlerResult(StockResponse<T> stockResponse, String code) {
        List<StockResult<T>> stockDataResults = stockResponse.getResult();
        if (stockDataResults != null) {
            if (stockDataResults.size() > 0) {
                StockResult stockMsgResult = stockDataResults.get(1);
                if (stockMsgResult == null) {
                    throw new ServiceException(code);
                }
                if ("OK".equals(stockMsgResult.getMsg().getErrorInfo())) {
                    StockResult<T> stockDataResult = stockDataResults.get(0);
                    return stockDataResult.getData();
                }
            }
        }
        throw new ServiceException(code);
    }
}
