package com.waben.stock.datalayer.buyrecord.init;

import com.waben.stock.datalayer.buyrecord.business.StockBusiness;
import com.waben.stock.datalayer.buyrecord.entity.BuyRecord;
import com.waben.stock.datalayer.buyrecord.service.BuyRecordService;
import com.waben.stock.datalayer.buyrecord.warpper.messagequeue.rabbit.EntrustApplyProducer;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.enums.EntrustState;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/12/8.
 * @desc 启动加载买入锁定的订单记录发送至委托买入队列
 */
@Component
public class BuyLockRecordInit implements CommandLineRunner {

//    @Autowired
    private BuyRecordService buyRecordService;
//    @Autowired
    private EntrustApplyProducer entrustApplyProducer;
//    @Autowired
    private StockBusiness stockBusiness;

    @Override
    public void run(String... args) throws Exception {
//        List<BuyRecord> buyRecords = buyRecordService.fetchByStateAndOrderByCreateTime(BuyRecordState.BUYLOCK);
//        for (BuyRecord buyRecord : buyRecords) {
//            SecuritiesStockEntrust securitiesStockEntrust = new SecuritiesStockEntrust();
//            securitiesStockEntrust.setBuyRecordId(buyRecord.getId());
//            securitiesStockEntrust.setSerialCode(buyRecord.getSerialCode());
//            securitiesStockEntrust.setInvestor(buyRecord.getInvestorId());
//            StockDto stockDto = stockBusiness.fetchWithExponentByCode(buyRecord.getStockCode());
//            securitiesStockEntrust.setStockName(stockDto.getName());
//            securitiesStockEntrust.setStockCode(stockDto.getCode());
//            securitiesStockEntrust.setExponent(stockDto.getStockExponentDto().getExponentCode());
//            securitiesStockEntrust.setEntrustNumber(buyRecord.getNumberOfStrand());
//            securitiesStockEntrust.setEntrustPrice(buyRecord.getBuyingPrice());
//            securitiesStockEntrust.setBuyRecordState(buyRecord.getState());
////            securitiesStockEntrust.setTradeSession(investorDto.getSecuritiesSession());
//            securitiesStockEntrust.setTradeNo(buyRecord.getTradeNo());
//            securitiesStockEntrust.setEntrustNo(buyRecord.getDelegateNumber());
//            securitiesStockEntrust.setEntrustState(EntrustState.HASBEENREPORTED);
////            entrustApplyProducer.entrustApplyBuyIn(securitiesStockEntrust);
//        }
    }
}
