package com.waben.stock.risk.init;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.DailyCalendar;
import org.quartz.impl.calendar.WeeklyCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.waben.stock.interfaces.constants.HolidayConstant;
import com.waben.stock.risk.schedule.WorkCalendar;
import com.waben.stock.risk.schedule.job.BuyInStopJob;
import com.waben.stock.risk.schedule.job.SellOutStopJob;
import com.waben.stock.risk.schedule.job.StockApplyEntrustBuyInJob;
import com.waben.stock.risk.schedule.job.StockApplyEntrustSellOutJob;
import com.waben.stock.risk.schedule.job.StockApplyEntrustWithdrawJob;
import com.waben.stock.risk.schedule.job.StockQuotationJob;
import com.waben.stock.risk.schedule.job.WithdrawStopJob;

/**
 * @author Created by yuyidi on 2017/11/6.
 * @desc 股票行情监控调度器
 */
@Component
public class StockMonitor implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(getClass());


    /***
     * @author yuyidi 2017-11-27 20:04:24
     * @method run
     * @param strings
     * @return void
     * @description 系统启动完成，启动任务调度器
     */
    public void run(String... strings) throws Exception {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        // 3、org.quartz.DateBuilder.evenMinuteDate <下一分钟>  -- 通过DateBuilder构建Date
        Date runTime = DateBuilder.evenMinuteDate(new Date());
        WeeklyCalendar workDay = new WeeklyCalendar();
        //排除特定的日期
        WorkCalendar workCalendar = new WorkCalendar(workDay, HolidayConstant.holiyday_2018);
        //排除在外的时间  通过使用invertTimeRange=true  表示倒置
        DailyCalendar am = new DailyCalendar(workCalendar, "09:30", "11:45");
        am.setInvertTimeRange(true);
        DailyCalendar pm = new DailyCalendar(workCalendar, "13:00", "15:45");
        pm.setInvertTimeRange(true);
        scheduler.addCalendar("calendarAM", am, false, false);
        scheduler.addCalendar("calendarPM", pm, false, false);
        scheduler.addCalendar("workCalendar", workCalendar, false, false);
        //拉取股票行情任务
        JobDetail jobQuotation = JobBuilder.newJob(StockQuotationJob.class).withIdentity("jobQuotation",
                "groupQuotation")
                .storeDurably(true)
                .build();
        SimpleTrigger stockQuotationAM = newTrigger().withIdentity("quotationAMTrigger", "groupQuotation").startAt
                (runTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(12).repeatForever())
                .forJob(jobQuotation)
                .modifiedByCalendar("calendarAM")
                .build();
        SimpleTrigger stockQuotationPM = newTrigger().withIdentity("quotationPMTrigger", "groupQuotation").startAt
                (runTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(12).repeatForever())
                .forJob(jobQuotation)
                .modifiedByCalendar("calendarPM")
                .build();

        CronScheduleBuilder scheduleEntrustBuilder = CronScheduleBuilder.cronSchedule("0 0 9,13 * * ?");
        CronScheduleBuilder scheduleBuilderAMStop = CronScheduleBuilder.cronSchedule("0 30 11 * * ?");
        CronScheduleBuilder scheduleBuilderPMStop = CronScheduleBuilder.cronSchedule("0 0 15 * * ?");
        //买入任务
        JobDetail jobBuyIn = JobBuilder.newJob(StockApplyEntrustBuyInJob.class).withIdentity("jobBuyIn", "groupBuyIn")
                .storeDurably(true)
                .build();
        JobDetail jobBuyInStop = JobBuilder.newJob(BuyInStopJob.class).withIdentity("jobBuyInStop", "groupBuyIn")
                .storeDurably(true)
                .build();

        Trigger buyInTriggerBegin = newTrigger().withIdentity("buyInTriggerBegin", "groupBuyIn").startAt(runTime)
                .withSchedule(scheduleEntrustBuilder)
                .modifiedByCalendar("workCalendar")
                .forJob(jobBuyIn)
                .build();

        Trigger buyInTriggerAMStop = newTrigger().withIdentity("buyInTriggerAMStop", "groupBuyIn").startAt(runTime)
                .withSchedule(scheduleBuilderAMStop)
                .modifiedByCalendar("workCalendar")
                .forJob(jobBuyInStop)
                .build();
        Trigger buyInTriggerPMStop = newTrigger().withIdentity("buyInTriggerPMStop", "groupBuyIn").startAt(runTime)
                .withSchedule(scheduleBuilderPMStop)
                .modifiedByCalendar("workCalendar")
                .forJob(jobBuyInStop)
                .build();

        //卖出任务
        JobDetail jobSellOut = JobBuilder.newJob(StockApplyEntrustSellOutJob.class).withIdentity("jobSellOut",
                "groupSellOut")
                .storeDurably(true)
                .build();
        JobDetail jobSellOutStop = JobBuilder.newJob(SellOutStopJob.class).withIdentity("jobSellOutStop",
                "groupSellOut")
                .storeDurably(true)
                .build();
        Trigger sellOutTriggerBegin = newTrigger().withIdentity("sellOutTriggerBegin", "groupSellOut").startAt(runTime)
                .withSchedule(scheduleEntrustBuilder)
                .modifiedByCalendar("workCalendar")
                .forJob(jobSellOut)
                .build();
        Trigger sellOutTriggerAMStop = newTrigger().withIdentity("sellOutTriggerAMStop", "groupSellOut").startAt
                (runTime)
                .withSchedule(scheduleBuilderAMStop)
                .modifiedByCalendar("workCalendar")
                .forJob(jobSellOutStop)
                .build();
        Trigger sellOutTriggerPMStop = newTrigger().withIdentity("sellOutTriggerPMStop", "groupSellOut").startAt
                (runTime)
                .withSchedule(scheduleBuilderPMStop)
                .modifiedByCalendar("workCalendar")
                .forJob(jobSellOutStop)
                .build();

        //撤单任务
        JobDetail jobWithdraw = JobBuilder.newJob(StockApplyEntrustWithdrawJob.class).withIdentity("jobWithdraw",
                "groupWithdraw")
                .storeDurably(true)
                .build();
        JobDetail jobWithdrawStop = JobBuilder.newJob(WithdrawStopJob.class).withIdentity("jobWithdrawStop",
                "groupWithdraw")
                .storeDurably(true)
                .build();
        Trigger withdrawTriggerBegin = newTrigger().withIdentity("withdrawTriggerBegin", "groupWithdraw").startAt(runTime)
                .withSchedule(scheduleEntrustBuilder)
                .modifiedByCalendar("workCalendar")
                .forJob(jobWithdraw)
                .build();
        Trigger withdrawTriggerAMStop = newTrigger().withIdentity("withdrawTriggerAMStop", "groupWithdraw").startAt
                (runTime)
                .withSchedule(scheduleBuilderAMStop)
                .modifiedByCalendar("workCalendar")
                .forJob(jobWithdrawStop)
                .build();
        Trigger withdrawTriggerPMStop = newTrigger().withIdentity("withdrawTriggerPMStop", "groupWithdraw").startAt
                (runTime)
                .withSchedule(scheduleBuilderPMStop)
                .modifiedByCalendar("workCalendar")
                .forJob(jobWithdrawStop)
                .build();

        scheduler.addJob(jobQuotation, true);
        scheduler.scheduleJob(stockQuotationAM);
        scheduler.scheduleJob(stockQuotationPM);

        scheduler.addJob(jobBuyIn, true);
        scheduler.scheduleJob(buyInTriggerBegin);
        scheduler.addJob(jobBuyInStop, true);
        scheduler.scheduleJob(buyInTriggerAMStop);
        scheduler.scheduleJob(buyInTriggerPMStop);

        scheduler.addJob(jobSellOut, true);
        scheduler.scheduleJob(sellOutTriggerBegin);
        scheduler.addJob(jobSellOutStop, true);
        scheduler.scheduleJob(sellOutTriggerAMStop);
        scheduler.scheduleJob(sellOutTriggerPMStop);

        scheduler.addJob(jobWithdraw, true);
        scheduler.scheduleJob(withdrawTriggerBegin);
        scheduler.addJob(jobWithdrawStop, true);
        scheduler.scheduleJob(withdrawTriggerAMStop);
        scheduler.scheduleJob(withdrawTriggerPMStop);

        scheduler.start();
    }
}
