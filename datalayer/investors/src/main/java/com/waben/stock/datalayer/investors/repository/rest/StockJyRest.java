package com.waben.stock.datalayer.investors.repository.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.enums.EntrustType;
import com.waben.stock.interfaces.pojo.stock.SecuritiesInterface;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.pojo.stock.stockjy.StockResponse;
import com.waben.stock.interfaces.pojo.stock.stockjy.StockResponseHander;
import com.waben.stock.interfaces.pojo.stock.stockjy.data.*;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.web.HttpRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@Component
public class StockJyRest extends StockResponseHander implements SecuritiesInterface {

    Logger logger = LoggerFactory.getLogger(getClass());
    //券商资金账户登录
    private String loginPath = "http://106.15.37.226:8445/stockjy/login";
    //券商资金账户股东账户查询
    private String holderPath = "http://106.15.37.226:8445/stockjy/holder";
    //券商鼓动账户下单
    private String entrustPath = "http://106.15.37.226:8445/stockjy/entrust";
    //券商委托单查询
    private String queryEntrusPath = "http://106.15.37.226:8445/stockjy/qryentrust";
    //资金信息
    private String moneyPath = "http://106.15.37.226:8445/stockjy/money";

    public StockLoginInfo login(String account, String password) {
        String loginUrl = loginPath+"?account_content={account_content}&password={password}";
        Map<String, String> params = new HashMap<>();
        params.put("account_content", account);
        params.put("password", password);
        String result = HttpRest.get(loginUrl, String.class, params);
        logger.info("券商资金账户登录,请求地址:{},请求结果:{}", loginUrl, result);
        StockResponse<StockLoginInfo> stockResponse = JacksonUtil.decode(result, new
                TypeReference<StockResponse<StockLoginInfo>>() {
                });
        return handlerResult(stockResponse, ExceptionConstant.INVESTOR_SECURITIES_LOGIN_EXCEPTION).get(0);
    }

    public StockMoney money(String tradeSession) {
        String moneyUrl = moneyPath + "?trade_session={trade_session}";
        Map<String, String> params = new HashMap<>();
        params.put("trade_session", tradeSession);
        String result = HttpRest.get(moneyUrl, String.class, params);
        logger.info("券商资金查询,请求地址:{},请求结果:{}", moneyUrl, result);
        StockResponse<StockMoney> stockResponse = JacksonUtil.decode(result, new
                TypeReference<StockResponse<StockMoney>>() {
                });
        return handlerResult(stockResponse, ExceptionConstant.INVESTOR_STOCKACCOUNT_MONEY_NOT_ENOUGH).get(0);
    }

    /***
     * @author Administrator 2017-12-01 10:18:09
     * @method retrieveStockHolder
     * @param tradeSession 资金账户登录session
     * @return com.waben.stock.interfaces.pojo.stock.stockjy.data.StockHolder
     * @description 获取资金账户的股东账户列表
     */
    public List<StockHolder> retrieveStockHolder(String tradeSession) {
        String holderUrl = holderPath + "?trade_session={trade_session}";
        Map<String, String> params = new HashMap<>();
        params.put("trade_session", tradeSession);
        String result = HttpRest.get(holderUrl, String.class, params);
        StockResponse<StockHolder> stockResponse = JacksonUtil.decode(result, new
                TypeReference<StockResponse<StockHolder>>() {
                });
        logger.info("获取资金账户股东账户列表:{}", result);
        return handlerResult(stockResponse, ExceptionConstant.INVESTOR_STOCKACCOUNT_NOT_EXIST);
    }

    /***
     * @author yuyidi 2017-12-01 11:03:26
     * @method buyRecordEntrust
     * @param  securitiesStockEntrust 点买交易记录
     * @param tradeSession 资金账户登录session
     * @param entrustType 委托下单买入卖出类型
     * @param stockAccount 股东账户
     * @return void
     * @description 点买交易记录下单
     */
    public String buyRecordEntrust(SecuritiesStockEntrust securitiesStockEntrust, String tradeSession, String
            stockAccount, String type,EntrustType entrustType) {
        String entrustUrl = entrustPath + "?trade_session={trade_session}&" +
                "exchange_type={exchange_type}&" +
                "stock_account={stock_account}&" +
                "stock_code={stock_code}&" +
                "entrust_amount={entrust_amount}&" +
                "entrust_price={entrust_price}&" +
                "entrust_bs={entrust_bs}&" +
                "entrust_prop=0";
        Map<String, String> params = new HashMap<>();
        params.put("trade_session", tradeSession);
        params.put("exchange_type", type);
        params.put("stock_account", stockAccount);
        params.put("stock_code", securitiesStockEntrust.getStockCode());
        params.put("entrust_amount", String.valueOf(securitiesStockEntrust.getEntrustNumber()));
        params.put("entrust_price", String.valueOf(securitiesStockEntrust.getEntrustPrice()));
        params.put("entrust_bs", entrustType.getType());
        String result = HttpRest.get(entrustUrl, String.class, params);
        logger.info("券商资金查询,请求地址:{},请求结果:{}", entrustUrl, result);
        StockResponse<StockEntrustResult> stockResponse = JacksonUtil.decode(result, new
                TypeReference<StockResponse<StockEntrustResult>>() {
                });
        StockEntrustResult stockEntrustResult = handlerResult(stockResponse, ExceptionConstant
                .INVESTOR_STOCKENTRUST_FETCH_ERROR).get(0);
        return stockEntrustResult.getEntrustNo();
    }

    /***
     * @author yuyidi 2017-12-01 14:20:16
     * @method queryStockByEntrust
     * @param tradeSession
     * @param entrust
     * @return java.util.List<com.waben.stock.interfaces.pojo.stock.stockjy.data.StockEntrustQueryResult>
     * @description 查询股票委托情况
     */
    public List<StockEntrustQueryResult> queryStockByEntrust(String tradeSession, String entrust) {
        String queryEntrusUrl = queryEntrusPath + "?trade_session={trade_session}&" +
                "entrust_no={entrust_no}&" +
                "request_num=50";
        Map<String, String> params = new HashMap<>();
        params.put("trade_session", tradeSession);
        params.put("entrust_no", entrust);
        String result = HttpRest.get(queryEntrusUrl, String.class, params);
        StockResponse<StockEntrustQueryResult> stockResponse = JacksonUtil.decode(result, new
                TypeReference<StockResponse<StockEntrustQueryResult>>() {
                });
        return handlerResult(stockResponse, ExceptionConstant.INVESTOR_STOCKENTRUST_FETCH_ERROR);
    }


}
