package com.waben.stock.datalayer.stockcontent;

import com.waben.stock.datalayer.stockcontent.entity.Stock;
import com.waben.stock.datalayer.stockcontent.entity.StockExponent;
import com.waben.stock.datalayer.stockcontent.pojo.Resonse;
import com.waben.stock.datalayer.stockcontent.pojo.StockVariety;
import com.waben.stock.datalayer.stockcontent.service.StockExponentService;
import com.waben.stock.datalayer.stockcontent.service.StockService;
import com.waben.stock.interfaces.util.JacksonUtil;
import com.waben.stock.interfaces.web.HttpRest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockContentApplicationTests {

    @Autowired
    private StockService stockService;
    @Autowired
    private StockExponentService stockExponentService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetStockList() {
        String url = "http://lemi.esongbai.com/order/order/getStockVariety.do?page={page}&pageSize={pageSize}";
        int page = 0, pageSize = 500;
        int index = 0;
        request(url, page, pageSize, index);

    }

    private Resonse request(String url, Integer page, Integer pageSize, int index) {
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        Resonse result = HttpRest.get(url, Resonse.class, params);
        if (result.getCode().equals("200") && result.getData().length != 0) {
            ++page;
            for (StockVariety stockVariety : result.getData()) {
                Stock stock = new Stock();
                stock.setName(stockVariety.getVarietyName());
                stock.setCode(stockVariety.getVarietyType());
                StockExponent stockExponent = stockExponentService.findStockExponent(stockVariety.getExchangeCode());
                stock.setExponent(stockExponent);
                stock.setStatus(true);
                stockService.saveStock(stock);
            }
            request(url, page, result.getPageSize(), index);
        }
        return result;
    }

}
