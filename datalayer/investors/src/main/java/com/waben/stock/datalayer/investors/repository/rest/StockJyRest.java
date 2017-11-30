package com.waben.stock.datalayer.investors.repository.rest;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.stock.stockjy.StockDataResult;
import com.waben.stock.interfaces.pojo.stock.stockjy.StockLoginInfo;
import com.waben.stock.interfaces.pojo.stock.stockjy.StockMsg;
import com.waben.stock.interfaces.pojo.stock.stockjy.StockResponse;
import com.waben.stock.interfaces.web.HttpRest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@Component
public class StockJyRest implements SecuritiesInterface {
    private String loginUrl = "http://106.15.37.226:8445/stockjy/login";

    public StockLoginInfo login(String account, String password) {
        loginUrl.concat("?account_content={accountContent}&password={password}");
        Map<String, String> params = new HashMap<>();
        params.put("account_content", account);
        params.put("password", password);
        StockResponse result = HttpRest.get(loginUrl, StockResponse.class, params);
        List<Object> stockDataResults = result.getResult();
        if (stockDataResults != null) {
            if (stockDataResults.size() > 0) {
                StockMsg stockMsg = (StockMsg) stockDataResults.get(1);
                if (stockMsg == null) {
                    throw new ServiceException(ExceptionConstant.INVESTOR_SECURITIES_LOGIN_EXCEPTION);
                }
                if ("ok".equals(stockMsg.getErrorInfo())) {
                    StockDataResult stockDataResult = (StockDataResult) stockDataResults.get(1);
                    return stockDataResult.getData().get(0);
                }
            }
        }
        throw new ServiceException(ExceptionConstant.INVESTOR_SECURITIES_LOGIN_EXCEPTION);
    }
}
