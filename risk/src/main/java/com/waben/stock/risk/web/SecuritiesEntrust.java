package com.waben.stock.risk.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.pojo.stock.SecuritiesInterface;
import com.waben.stock.interfaces.pojo.stock.stockjy.StockResponse;
import com.waben.stock.interfaces.pojo.stock.stockjy.StockResponseHander;
import com.waben.stock.interfaces.pojo.stock.stockjy.data.StockEntrustQueryResult;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.web.HttpRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/***
 * @author yuyidi 2017-12-05 11:13:57
 * @class com.waben.stock.risk.web.SecuritiesEntrust
 * @description 券商委托单http请求
 */
@Component
public class SecuritiesEntrust extends StockResponseHander implements SecuritiesInterface {

    Logger logger = LoggerFactory.getLogger(getClass());

    //券商委托单查询
    private String queryEntrustPath = "http://106.15.37.226:8445/stockjy/qryentrust";

    public StockEntrustQueryResult queryEntrust(String tradeSession, String entrustNo) {
        String loginUrl = queryEntrustPath + "?trade_session={trade_session}&entrust_no={entrust_no}";
        Map<String, String> params = new HashMap<>();
        params.put("trade_session", tradeSession);
        params.put("entrust_no", entrustNo);
        String result = HttpRest.get(loginUrl, String.class, params);
        logger.info("券商委托单查询,请求地址:{},请求结果:{}", loginUrl, result);
        StockResponse<StockEntrustQueryResult> stockResponse = JacksonUtil.decode(result, new
                TypeReference<StockResponse<StockEntrustQueryResult>>() {
                });
        return handlerResult(stockResponse, ExceptionConstant.INVESTOR_SECURITIES_LOGIN_EXCEPTION).get(0);
    }

}
