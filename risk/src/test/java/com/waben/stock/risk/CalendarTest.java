package com.waben.stock.risk;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarTest {
    static Logger logger = LoggerFactory.getLogger(CalendarTest.class);
    public static void main(String[] args) throws ParseException, InterruptedException {
        long a = 1517821080124L;
        long b = 1517846400000L;
        logger.info("result:{}",(a-b)/1000);
        Date date1 = new Date();
        Thread.sleep(3000);
        Date date = new Date();
        logger.info("过期时间:{},当前时间:{}", date1.getTime(), date.getTime());
        logger.info("结果：{}",date1.getTime()-date.getTime());
    }
}
