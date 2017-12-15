package com.waben.stock.risk.init;

import com.netflix.discovery.converters.Auto;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.enums.EntrustState;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.risk.business.BuyRecordBusiness;
import com.waben.stock.risk.business.StockBusiness;
import com.waben.stock.risk.container.StockApplyEntrustBuyInContainer;
import com.waben.stock.risk.service.BuyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/12/14.
 * @desc
 */
@Component
public class StockBuyLockInitialize implements CommandLineRunner {

    @Autowired
    private BuyRecordBusiness buyRecordBusiness;
    @Autowired
    private StockBusiness stockBusiness;
    @Autowired
    private StockApplyEntrustBuyInContainer stockApplyEntrustBuyInContainer;

    @Override
    public void run(String... args) throws Exception {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
        List<BuyRecordDto> buyRecords = buyRecordBusiness.buyRecordsWithBuyLock();
        for (BuyRecordDto buyRecord : buyRecords) {
            SecuritiesStockEntrust securitiesStockEntrust = new SecuritiesStockEntrust();
            securitiesStockEntrust.setBuyRecordId(buyRecord.getId());
            securitiesStockEntrust.setSerialCode(buyRecord.getSerialCode());
            securitiesStockEntrust.setInvestor(buyRecord.getInvestorId());
            StockDto stockDto = stockBusiness.fetchByCode(buyRecord.getStockCode());
            securitiesStockEntrust.setStockName(stockDto.getName());
            securitiesStockEntrust.setStockCode(stockDto.getCode());
            securitiesStockEntrust.setExponent(stockDto.getStockExponentDto().getExponentCode());
            securitiesStockEntrust.setEntrustNumber(buyRecord.getNumberOfStrand());
            securitiesStockEntrust.setEntrustPrice(buyRecord.getBuyingPrice());
            securitiesStockEntrust.setBuyRecordState(buyRecord.getState());
//            securitiesStockEntrust.setTradeSession(investorDto.getSecuritiesSession());
            securitiesStockEntrust.setTradeNo(buyRecord.getTradeNo());
            securitiesStockEntrust.setEntrustNo(buyRecord.getDelegateNumber());
            securitiesStockEntrust.setEntrustState(EntrustState.HASBEENREPORTED);
            stockApplyEntrustBuyInContainer.add(securitiesStockEntrust);
        }
    }
}
