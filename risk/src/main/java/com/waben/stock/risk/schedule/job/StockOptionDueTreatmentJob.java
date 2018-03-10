package com.waben.stock.risk.schedule.job;

import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.risk.business.StockOptionTradeBusiness;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StockOptionDueTreatmentJob implements InterruptableJob {
    Logger logger = LoggerFactory.getLogger(getClass());
    private Boolean interrupted = false;
    @Autowired
    private StockOptionTradeBusiness stockOptionTradeBusiness;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        List<StockOptionTradeDto> stockOptionTradeDtos = stockOptionTradeBusiness.stockOptionsWithTurnover();
        for (StockOptionTradeDto stockOptionTradeDto : stockOptionTradeDtos) {
            Date expireTime = stockOptionTradeDto.getExpireTime();
            Date currentTime = new Date();
            BigDecimal buyingPrice = stockOptionTradeDto.getBuyingPrice();
            if(fmt.format(expireTime).equals(fmt.format(currentTime))) {

            }
        }
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        logger.info("期权到期处理任务被中断");
        interrupted = true;
    }
}
