package com.waben.stock.risk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.waben.stock.interfaces.pojo.stock.stockjy.StockResponse;
import com.waben.stock.interfaces.pojo.stock.stockjy.data.StockEntrustQueryResult;
import com.waben.stock.interfaces.pojo.stock.stockjy.data.StockLoginInfo;
import com.waben.stock.interfaces.util.JacksonUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Created by yuyidi on 2017/12/8.
 * @desc
 */
public class SimpleTest {

    @Test
    public void testEntrustResult() {
        String json = "{\"result\":[{\"data\":[{\"business_amount\":\"100.00\",\"business_price\":\"13.050\"," +
                "\"entrust_amount\":\"100.00\",\"entrust_bs\":\"1\",\"entrust_date\":\"20171208\"," +
                "\"entrust_no\":\"144\",\"entrust_price\":\"13.500\",\"entrust_status\":\"8\"," +
                "\"entrust_time\":\"145150\",\"exchange_type\":\"2\"," +
                "\"position_str\":\"20171208021451508400002200000144\",\"stock_account\":\"0070001553\"," +
                "\"stock_code\":\"000001\",\"stock_name\":\"平安银行\",\"withdraw_flag\":\"0\"}]}," +
                "{\"msg\":{\"error_info\":\"OK\",\"error_no\":\"3000\"}}]}";
        StockResponse<StockEntrustQueryResult> stockResponse = JacksonUtil.decode(json, new
                TypeReference<StockResponse<StockEntrustQueryResult>>() {});
        System.out.println(JacksonUtil.encode(stockResponse));
    }

    @Test
    public void testEntrustSession() {
        String json = "{\"result\":[{\"data\":[{\"client_id\":\"70001553\",\"client_name\":\"日终测试1\"," +
                "\"fund_account\":\"70001553\",\"trade_session\":\"0722f5795befa1477a9f1ca096e8ac67011512715010\"}]}," +
                "{\"msg\":{\"error_info\":\"OK\",\"error_no\":\"3000\"}}]}";
        StockResponse<StockLoginInfo> stockResponse = JacksonUtil.decode(json, new
                TypeReference<StockResponse<StockLoginInfo>>() {});
        System.out.println(JacksonUtil.encode(stockResponse));
    }

    @Test
    public void testBigDecimal() {
        BigDecimal amountValue = new BigDecimal(10000);
        BigDecimal price = new BigDecimal(13.45);
        System.out.println(amountValue.divide(price,2, RoundingMode.HALF_UP).intValue());
    }

    @Test
    public void testCard() {
        System.out.println(ChickID("421126199206302514"));
    }

    public boolean ChickID(String text) {
        if (text != null) {
            int correct = new IdCardUtil(text).isCorrect();
            if (0 == correct) {// 符合规范
                return true;
            }
        }
        return false;
    }
}
