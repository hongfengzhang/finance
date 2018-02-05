package com.waben.stock.datalayer.investors.schedule.job;

import com.waben.stock.datalayer.investors.container.StockApplyEntrustBuyInContainer;
import com.waben.stock.datalayer.investors.container.StockApplyEntrustSellOutContainer;
import com.waben.stock.datalayer.investors.reference.BuyRecordReference;
import com.waben.stock.datalayer.investors.service.InvestorService;
import com.waben.stock.datalayer.investors.warpper.ApplicationContextBeanFactory;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
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
    @Autowired
    private InvestorService investorService;
//    @Autowired
//    private BuyRecordReference buyRecordReference;
    private Boolean interrupted = false;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("委托卖出容器对象:{},当前对象{}", securitiesStockEntrustContainer, this);
//        SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
        while (!interrupted) {
            Map<Long, SecuritiesStockEntrust> sellOutContainer = securitiesStockEntrustContainer.getSellOutContainer();
            for (Map.Entry<Long, SecuritiesStockEntrust> entry : sellOutContainer.entrySet()) {
                try {
                    SecuritiesStockEntrust securitiesStockEntrust = entry.getValue();
//                    Date entrustTime = securitiesStockEntrust.getEntrustTime();
//                    Date currentTime = new Date();
//                    String currentDay = sdfDate.format(currentTime);
//                    String entrustDay = sdfDate.format(entrustTime);
//                    if (currentDay.compareToIgnoreCase(entrustDay) >= 1) {
//                        logger.info("当前时间大于委托卖出时间执行废单:{}，点买记录:{}", currentDay.compareToIgnoreCase(entrustDay), securitiesStockEntrust.getBuyRecordId());
//                        buyRecordReference.revoke(securitiesStockEntrust.getBuyRecordId());
//                        continue;
//                    }
                    logger.info("自动卖出订单数据:{}", JacksonUtil.encode(securitiesStockEntrust));
                    BuyRecordDto buyRecordDto = investorService.voluntarilyApplySellOut(securitiesStockEntrust, WindControlType.PUBLISHERAPPLY.getIndex());
                    logger.info("委托卖出成功：{}", JacksonUtil.encode(buyRecordDto));
                    sellOutContainer.remove(securitiesStockEntrust.getBuyRecordId());
                } catch (Exception exception) {
                    logger.info("卖出异常：{}", exception);
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
