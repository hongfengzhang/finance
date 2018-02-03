package com.waben.stock.datalayer.investors.schedule.job;

import com.waben.stock.datalayer.investors.business.BuyRecordBusiness;
import com.waben.stock.datalayer.investors.container.StockApplyEntrustBuyInContainer;
import com.waben.stock.datalayer.investors.reference.BuyRecordReference;
import com.waben.stock.datalayer.investors.service.InvestorService;
import com.waben.stock.datalayer.investors.warpper.ApplicationContextBeanFactory;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.enums.EntrustState;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.pojo.stock.stockjy.data.StockEntrustQueryResult;
import com.waben.stock.interfaces.util.JacksonUtil;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author Created by yuyidi on 2017/12/15.
 * @desc
 */
//@Component
public class StockApplyEntrustBuyInJob implements InterruptableJob {

    Logger logger = LoggerFactory.getLogger(getClass());

    private StockApplyEntrustBuyInContainer securitiesStockEntrustContainer = ApplicationContextBeanFactory.getBean
            (StockApplyEntrustBuyInContainer.class);
    @Autowired
    private InvestorService investorService;
    @Autowired
    private BuyRecordReference buyRecordReference;
    private Boolean interrupted = false;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("委托买入容器对象:{},当前对象{}", securitiesStockEntrustContainer, this);
        SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        while (!interrupted) {
            try {
                Map<Long, SecuritiesStockEntrust> buyInContainer = securitiesStockEntrustContainer.getBuyInContainer();
                for (Map.Entry<Long, SecuritiesStockEntrust> entry : buyInContainer.entrySet()) {
                    SecuritiesStockEntrust securitiesStockEntrust = entry.getValue();
                    Date entrustTime = securitiesStockEntrust.getEntrustTime();
                    Date currentTime = new Date();
                    String currentDay = sdfDate.format(currentTime);
                    String entrustDay = sdfDate.format(entrustTime);
                    if(currentDay.compareToIgnoreCase(entrustDay)>=1) {
                        logger.info("当前时间大于委托买入时间执行废单:{}，点买记录:{}", currentDay.compareToIgnoreCase(entrustDay),securitiesStockEntrust.getBuyRecordId());
                        buyRecordReference.revoke(securitiesStockEntrust.getBuyRecordId());
                        continue;
                    }
                    logger.info("自动买入订单数据:{}", JacksonUtil.encode(securitiesStockEntrust));
                    BuyRecordDto buyRecordDto = investorService.voluntarilyApplyBuyIn(securitiesStockEntrust);
                    logger.info("委托买入成功：{}",JacksonUtil.encode(buyRecordDto));
                    buyInContainer.remove(securitiesStockEntrust.getBuyRecordId());
                }
            }catch (Exception exception) {
                logger.info("买入异常：{}",exception.getMessage());
            }
        }
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        logger.info("股票申请委托买入任务被中断");
        interrupted = true;
    }
}
