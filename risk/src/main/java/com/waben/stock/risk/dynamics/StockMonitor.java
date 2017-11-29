package com.waben.stock.risk.dynamics;

import com.waben.stock.risk.schedule.WorkCalendar;
import com.waben.stock.risk.schedule.job.StockQuotationJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.DailyCalendar;
import org.quartz.impl.calendar.WeeklyCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static org.quartz.JobBuilder.newJob;
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
        //系统启动成功，加载所有需要监控的股票代码行情。
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        WeeklyCalendar workDay = new WeeklyCalendar();
        //排除特定的日期
        WorkCalendar workCalendar = new WorkCalendar(workDay, "2018-01-01");
        DailyCalendar quotationAM = new DailyCalendar(workCalendar, "15:30", "15:32");
        quotationAM.setInvertTimeRange(true);

        DailyCalendar quotationPM = new DailyCalendar(workCalendar, "15:33", "15:35");
        quotationPM.setInvertTimeRange(true);
        //向scheduler 中注册日历
        JobDetail stockMontorJob = newJob(StockQuotationJob.class)
                .withIdentity("quotationJob", "stock")
                .storeDurably()
//                .requestRecovery(false)
                .build();
        scheduler.addCalendar("quotationAM", quotationAM, false, false);
        SimpleTrigger triggerAM = newTrigger().withIdentity("quotationAMTrigger", "stock").startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever())
                .forJob(stockMontorJob)
                .modifiedByCalendar("quotationAM")
                .build();
        scheduler.addCalendar("quotationPM", quotationPM, false, false);
        SimpleTrigger triggerPM = newTrigger().withIdentity("quotationPMTrigger", "stock").startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever())
                .forJob(stockMontorJob)
                .modifiedByCalendar("quotationPM")
                .build();
        scheduler.addJob(stockMontorJob, true);
        scheduler.scheduleJob(triggerAM);
        scheduler.scheduleJob(triggerPM);
        //10秒后启动任务调度器
        scheduler.startDelayed(10 * 1000);
        scheduler.start();
    }
}
