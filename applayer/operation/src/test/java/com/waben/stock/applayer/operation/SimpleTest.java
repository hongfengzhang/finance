package com.waben.stock.applayer.operation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.waben.stock.applayer.operation.dto.websocket.StockRequestMessage;
import com.waben.stock.interfaces.pojo.stock.quotation.Resonse;
import com.waben.stock.interfaces.pojo.stock.quotation.StockMarket;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.web.HttpRest;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by yuyidi on 2017/11/15.
 * @desc
 */
public class SimpleTest {

    @Test
    public void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        System.out.println(passwordEncoder.encode("wangbei"));
    }

    @Test
    public void testStock() {
        String context = "http://lemi.esongbai.com/stk/stk";
        List<StockRequestMessage> requestMessages = new ArrayList<>();
        StockRequestMessage stockRequestMessage1 = new StockRequestMessage();
        stockRequestMessage1.setCode("000001");
        StockRequestMessage stockRequestMessage2 = new StockRequestMessage();
        stockRequestMessage2.setCode("000002");
        requestMessages.add(stockRequestMessage1);
        requestMessages.add(stockRequestMessage2);
        StringBuilder codes = new StringBuilder();
        for (StockRequestMessage stockRequestMessage : requestMessages) {
            codes.append(stockRequestMessage.getCode()).append(",");
        }
        String params = codes.toString().substring(0, codes.toString().length() - 1);
        String url = context + "/list.do?codes=" + params;
        System.out.println(url);
        Resonse<StockMarket> resonse = HttpRest.get(url, Resonse.class);
        List<StockMarket> result = JacksonUtil.decode("", new TypeReference<List<StockMarket>>() {
        });
        System.out.println();
    }
}
