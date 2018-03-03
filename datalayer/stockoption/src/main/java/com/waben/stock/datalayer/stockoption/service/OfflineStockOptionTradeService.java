package com.waben.stock.datalayer.stockoption.service;

import com.waben.stock.datalayer.stockoption.entity.InquiryResult;
import com.waben.stock.datalayer.stockoption.entity.OfflineStockOptionTrade;
import com.waben.stock.datalayer.stockoption.entity.StockOptionTrade;
import com.waben.stock.datalayer.stockoption.repository.OfflineStockOptionTradeDao;
import com.waben.stock.datalayer.stockoption.repository.StockOptionTradeDao;
import com.waben.stock.interfaces.enums.OfflineStockOptionTradeState;
import com.waben.stock.interfaces.enums.StockOptionTradeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Service
public class OfflineStockOptionTradeService {
    @Autowired
    private OfflineStockOptionTradeDao offlineStockOptionTradeDao;
    @Autowired
    private StockOptionTradeDao stockOptionTradeDao;
    @Transactional
    public OfflineStockOptionTrade save(OfflineStockOptionTrade offlineStockOptionTrade) {
        StockOptionTrade stockOptionTrade = stockOptionTradeDao.retrieve(offlineStockOptionTrade.getId());
        //组装线下交易信息
        offlineStockOptionTrade.setId(null);
        offlineStockOptionTrade.setState(OfflineStockOptionTradeState.TURNOVER);
        offlineStockOptionTrade.setStockCode(stockOptionTrade.getStockCode());
        offlineStockOptionTrade.setStockName(stockOptionTrade.getStockName());
        offlineStockOptionTrade.setNominalAmount(stockOptionTrade.getNominalAmount());
        offlineStockOptionTrade.setRightMoney(stockOptionTrade.getNominalAmount().multiply(offlineStockOptionTrade.getRightMoneyRatio()));
        offlineStockOptionTrade.setCycle(stockOptionTrade.getCycle());
        offlineStockOptionTrade.setBuyingTime(new Date());
        //到期时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH,stockOptionTrade.getCycle());
        offlineStockOptionTrade.setExpireTime(calendar.getTime());
        //将线下交易信息添加到数据库
        OfflineStockOptionTrade result = offlineStockOptionTradeDao.create(offlineStockOptionTrade);
        //修改申购交易信息
        stockOptionTrade.setBuyingPrice(offlineStockOptionTrade.getBuyingPrice());
        stockOptionTrade.setOfflineTrade(result);
        stockOptionTrade.setState(StockOptionTradeState.TURNOVER);
        stockOptionTradeDao.update(stockOptionTrade);
        return result;
    }
    @Transactional
    public OfflineStockOptionTrade settlement(Long id, BigDecimal sellingPrice) {
        //机构结算
        OfflineStockOptionTrade offlineStockOptionTrade = offlineStockOptionTradeDao.retrieve(id);
        offlineStockOptionTrade.setSellingPrice(sellingPrice);
        offlineStockOptionTrade.setSellingTime(new Date());
        BigDecimal profit = sellingPrice.subtract(offlineStockOptionTrade.getBuyingPrice()).divide(sellingPrice);
        offlineStockOptionTrade.setProfit(profit);
        //修改申购信息卖出价格
        StockOptionTrade stockOptionTrade = stockOptionTradeDao.retrieveByOfflineTrade(id);
        stockOptionTrade.setSellingTime(new Date());
        stockOptionTrade.setSellingPrice(sellingPrice);
        stockOptionTrade.setProfit(profit);
        stockOptionTradeDao.update(stockOptionTrade);

        return offlineStockOptionTradeDao.update(offlineStockOptionTrade);
    }
}
