package com.waben.stock.datalayer.investors.init;

import com.waben.stock.datalayer.investors.business.BuyRecordBusiness;
import com.waben.stock.datalayer.investors.container.StockApplyEntrustBuyInContainer;
import com.waben.stock.datalayer.investors.container.StockApplyEntrustSellOutContainer;
import com.waben.stock.datalayer.investors.warpper.ApplicationContextBeanFactory;
import com.waben.stock.datalayer.investors.web.StockQuotationHttp;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.pojo.stock.quotation.StockMarket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Created by yuyidi on 2017/12/14.
 * @desc
 */
@Component
//@Order(Ordered.LOWEST_PRECEDENCE+100)
public class StockSellOutInitialize implements CommandLineRunner {

    @Autowired
    private BuyRecordBusiness buyRecordBusiness;

    @Autowired
    private StockApplyEntrustSellOutContainer stockApplyEntrustSellOutContainer;
    @Autowired
    private StockQuotationHttp stockQuotationHttp;
    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void run(String... args) throws Exception {
        //拉取最新股票详情
        List<BuyRecordDto> buyRecords = buyRecordBusiness.buyRecordsWithSellOut();
        if(buyRecords.size()==0){
            logger.info("没有卖出中的订单");
            return;
        }
        Set<String> codes = new HashSet();
        for (BuyRecordDto buyRecord : buyRecords) {
            codes.add(buyRecord.getStockCode());
        }
        List<String> codePrams = new ArrayList();
        codePrams.addAll(codes);
        List<StockMarket> quotations = stockQuotationHttp.fetQuotationByCode(codePrams);
        logger.info("获取卖出中的点买交易记录个数：{}", buyRecords.size());
        for (BuyRecordDto buyRecord : buyRecords) {
            SecuritiesStockEntrust securitiesStockEntrust = new SecuritiesStockEntrust();
            securitiesStockEntrust.setBuyRecordId(buyRecord.getId());
            securitiesStockEntrust.setSerialCode(buyRecord.getSerialCode());
            securitiesStockEntrust.setEntrustNumber(buyRecord.getNumberOfStrand());
            securitiesStockEntrust.setBuyRecordState(buyRecord.getState());
            securitiesStockEntrust.setStockCode(buyRecord.getStockCode());
            securitiesStockEntrust.setEntrustTime(buyRecord.getUpdateTime());
            securitiesStockEntrust.setWindControlType(buyRecord.getWindControlTypes());
            for(StockMarket stockMarket: quotations) {
                if(stockMarket.getInstrumentId().equals(buyRecord.getStockCode())) {
                    securitiesStockEntrust.setEntrustPrice(stockMarket.getDownLimitPrice());
                    break;
                }
            }
            stockApplyEntrustSellOutContainer.add(securitiesStockEntrust);
        }
    }
}
