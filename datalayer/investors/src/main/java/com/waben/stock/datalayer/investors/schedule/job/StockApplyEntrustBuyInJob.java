package com.waben.stock.datalayer.investors.schedule.job;

import com.waben.stock.datalayer.investors.business.BuyRecordBusiness;
import com.waben.stock.datalayer.investors.container.StockApplyEntrustBuyInContainer;
import com.waben.stock.datalayer.investors.reference.BuyRecordReference;
import com.waben.stock.datalayer.investors.service.InvestorService;
import com.waben.stock.datalayer.investors.warpper.ApplicationContextBeanFactory;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.enums.BuyRecordState;
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
    private InvestorService investorService = ApplicationContextBeanFactory.getBean(InvestorService.class);
    private BuyRecordReference buyRecordReference = ApplicationContextBeanFactory.getBean(BuyRecordReference.class);
    private Boolean interrupted = false;
    private long millisOfDay = 24 * 60 * 60 * 1000;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("委托买入容器对象:{},当前对象{}", securitiesStockEntrustContainer, this);
        while (!interrupted) {
            try {
                Map<Long, SecuritiesStockEntrust> buyInContainer = securitiesStockEntrustContainer.getBuyInContainer();
                for (Map.Entry<Long, SecuritiesStockEntrust> entry : buyInContainer.entrySet()) {
                    SecuritiesStockEntrust securitiesStockEntrust = entry.getValue();
                    Date entrustTime = securitiesStockEntrust.getEntrustTime();
                    logger.info("自动买入订单数据:{}", JacksonUtil.encode(securitiesStockEntrust));
                    Date currentTime = new Date();
                    long currentDay = currentTime.getTime()/millisOfDay;
                    long entrustDay = entrustTime.getTime()/millisOfDay;
                    logger.info("当前时间：{},过期时间：{}",currentDay,entrustDay);
                    if(currentDay-entrustDay>=1) {
                        logger.info("当前时间大于委托买入时间执行废单:{}，点买记录:{}", currentDay-entrustDay,securitiesStockEntrust.getBuyRecordId());
                        buyRecordReference.revoke(securitiesStockEntrust.getBuyRecordId());
                        buyInContainer.remove(securitiesStockEntrust.getBuyRecordId());
                        continue;
                    }
                    BuyRecordDto buyRecordDto = investorService.voluntarilyApplyBuyIn(securitiesStockEntrust);
                    if(BuyRecordState.BUYLOCK.equals(buyRecordDto.getState())) {
                        logger.info("委托买入成功：{}",JacksonUtil.encode(buyRecordDto));
                        buyInContainer.remove(securitiesStockEntrust.getBuyRecordId());
                    }
                }
            }catch (Exception ex) {
            	ex.printStackTrace();
                logger.error("买入异常：{}", ex.getMessage());
            }
        }
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        logger.info("股票申请委托买入任务被中断");
        interrupted = true;
    }
}
