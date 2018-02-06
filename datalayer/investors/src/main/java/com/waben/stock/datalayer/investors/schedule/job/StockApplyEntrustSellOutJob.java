package com.waben.stock.datalayer.investors.schedule.job;

import com.waben.stock.datalayer.investors.container.StockApplyEntrustBuyInContainer;
import com.waben.stock.datalayer.investors.container.StockApplyEntrustSellOutContainer;
import com.waben.stock.datalayer.investors.reference.BuyRecordReference;
import com.waben.stock.datalayer.investors.service.InvestorService;
import com.waben.stock.datalayer.investors.warpper.ApplicationContextBeanFactory;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.enums.WindControlType;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.util.JacksonUtil;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author Created by yuyidi on 2017/12/15.
 * @desc
 */
//@Component
public class StockApplyEntrustSellOutJob implements InterruptableJob {

    Logger logger = LoggerFactory.getLogger(getClass());

    private StockApplyEntrustSellOutContainer securitiesStockEntrustContainer = ApplicationContextBeanFactory.getBean
            (StockApplyEntrustSellOutContainer.class);
    private InvestorService investorService = ApplicationContextBeanFactory.getBean(InvestorService.class);
    private Boolean interrupted = false;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        while (!interrupted) {
            Map<Long, SecuritiesStockEntrust> sellOutContainer = securitiesStockEntrustContainer.getSellOutContainer();
            for (Map.Entry<Long, SecuritiesStockEntrust> entry : sellOutContainer.entrySet()) {
                logger.info("委托卖出容器对象:{}", sellOutContainer.size());
                try {
                    SecuritiesStockEntrust securitiesStockEntrust = entry.getValue();
                    logger.info("自动卖出订单数据:{}", JacksonUtil.encode(securitiesStockEntrust));
                    BuyRecordDto buyRecordDto = investorService.voluntarilyApplySellOut(securitiesStockEntrust);
                    logger.info("委托卖出成功：{}", JacksonUtil.encode(buyRecordDto));
                    if(BuyRecordState.SELLLOCK.equals(buyRecordDto.getState())) {
                        sellOutContainer.remove(securitiesStockEntrust.getBuyRecordId());
                    }
                 } catch (Exception ex) {
                	ex.printStackTrace();
                    logger.error("卖出异常：{}", ex.getMessage());
                }
            }
        }
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        logger.info("股票申请委托卖出任务被中断");
        interrupted = true;
    }
}
