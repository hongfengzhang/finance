package com.waben.stock.datalayer.investors;

import com.waben.stock.interfaces.pojo.stock.stockjy.*;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.web.HttpRest;
import org.junit.Test;

import java.util.*;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
public class SimpleTest {

    @Test
    public void stockLogin() {
        String url = "http://106.15.37.226:8445/stockjy/login?account_content={accountContent}&password={password}";
        Map<String, String> params = new HashMap<>();
        params.put("accountContent", "70001553");
        params.put("password", "111111");
//        StockResponse result = HttpRest.get(url, StockResponse.class, params);
        StockResponse result = HttpRest.get(url, StockResponse.class, params);
        System.out.println(JacksonUtil.encode(result));
    }

    @Test
    public void simpleJson() {
//        StockResponse stockResponse = new StockResponse();
//        StockResult stockResult = new StockResult();
//        StockLoginInfo stockLoginInfo = new StockLoginInfo();
//        stockLoginInfo.setClientId("11111");
//        List<StockLoginInfo> data = new ArrayList<>();
//        data.add(stockLoginInfo);
//        StockMsg stockMsg = new StockMsg();
//        stockMsg.setErrorNo("3000");
//        stockResult.setData(data);
//        stockResult.setMsg(stockMsg);
//        stockResponse.setResult(new StockResult[]{stockResult});
//

        StockResponse stockResponse = new StockResponse();

        StockLoginInfo stockLoginInfo = new StockLoginInfo();
        stockLoginInfo.setClientId("11111");
        List<StockLoginInfo> data = new ArrayList<>();
        data.add(stockLoginInfo);
        StockMsgResult stockMsgResult = new StockMsgResult();
        StockMsg stockMsg = new StockMsg();
        stockMsg.setErrorNo("3000");
        stockMsgResult.setMsg(stockMsg);
//        StockResult stockResult = new StockResult();
//        stockResult.setData(data);
//        stockResult.setMsg(stockMsg);
        StockDataResult dataResult = new StockDataResult();
        dataResult.setData(data);
        List<Object> objects = new ArrayList<>();
        objects.add(dataResult);
        objects.add(stockMsgResult);
        stockResponse.setResult(objects);
        System.out.println(JacksonUtil.encode(stockResponse));
    }
}
