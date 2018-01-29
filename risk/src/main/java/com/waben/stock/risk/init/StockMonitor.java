package com.waben.stock.risk.init;

import com.waben.stock.interfaces.constants.HolidayConstant;
import com.waben.stock.risk.schedule.WorkCalendar;
import com.waben.stock.risk.schedule.job.*;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.DailyCalendar;
import org.quartz.impl.calendar.WeeklyCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

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
        DailyCalendar pm = new DailyCalendar(workCalendar, "13:30", "15:15");
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

        CronScheduleBuilder scheduleEntrustBuilder = CronScheduleBuilder.cronSchedule("0 30 9,13 * * ?");
        CronScheduleBuilder scheduleBuilderAMStop = CronScheduleBuilder.cronSchedule("0 45 11 * * ?");
        CronScheduleBuilder scheduleBuilderPMStop = CronScheduleBuilder.cronSchedule("0 15 15 * * ?");

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
//        ListenerManager listenerManager = scheduler.getListenerManager();
//        JobListener listener = new BuyInJobListener();
//        Matcher<JobKey> matcher = KeyMatcher.keyEquals(job.getKey());
//        listenerManager.addJobListener(listener, matcher);
//        TriggerListener triggerListener = new BuyInTriggerListener();
//        Matcher<TriggerKey> triggerAMKeyMatcher = KeyMatcher.keyEquals(triggerAMStop.getKey());
//        Matcher<TriggerKey> triggerPMKeyMatcher = KeyMatcher.keyEquals(triggerPMStop.getKey());
//        listenerManager.addTriggerListener(triggerListener,triggerAMKeyMatcher);
//        listenerManager.addTriggerListener(triggerListener,triggerPMKeyMatcher);
//        Set<Trigger> triggers = new HashSet<>();
//        triggers.add(trigger);
//        triggers.add(triggerAMStop);
//        triggers.add(triggerPMStop);
//        sched.scheduleJob(job,triggers,true);
//        sched.scheduleJob(job2, triggers,true);

        scheduler.addJob(jobQuotation, true);
        scheduler.scheduleJob(stockQuotationAM);
        scheduler.scheduleJob(stockQuotationPM);

        scheduler.addJob(jobBuyIn, true);
        scheduler.scheduleJob(buyInTriggerBegin);
        scheduler.addJob(jobBuyInStop, true);
        scheduler.scheduleJob(buyInTriggerAMStop);
        scheduler.scheduleJob(buyInTriggerPMStop);

//
        scheduler.addJob(jobSellOut, true);
        scheduler.scheduleJob(sellOutTriggerBegin);
        scheduler.addJob(jobSellOutStop, true);
        scheduler.scheduleJob(sellOutTriggerAMStop);
        scheduler.scheduleJob(sellOutTriggerPMStop);
        scheduler.start();
    }
}
