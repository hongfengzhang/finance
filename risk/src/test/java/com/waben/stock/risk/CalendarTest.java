package com.waben.stock.risk;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarTest {
    static Logger logger = LoggerFactory.getLogger(CalendarTest.class);
    public static void main(String[] args) throws ParseException, InterruptedException {
//        long a = 1517821080124L;
//        long b = 1517846400000L;
//        logger.info("result:{}",(a-b)/1000);
//        Date date1 = new Date();
//        Thread.sleep(3000);
//        Date date = new Date();
//        logger.info("过期时间:{},当前时间:{}", date1.getTime(), date.getTime());
//        logger.info("结果：{}",date1.getTime()-date.getTime());
//        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
//        Date currentDay = new Date();
//        Date parse = sdf.parse(DateFormat.getTimeInstance().format(currentDay));
//        logger.info("{},{}",parse, sdf.format(parse));
//        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
//        String parse1 = "2018-02-05 00:00:00";
//        String parse2 = "2018-02-05 00:00:00";
//        logger.info("result:{}",parse1.compareToIgnoreCase(parse2));
//        String s = "14:40:00";
//        logger.info("current:{}",s);
//        String s1 = "14:40:00";
//        logger.info("result:{}",s.compareToIgnoreCase(s1));
//        if(parse1.compareToIgnoreCase(parse2)>=0&&s.compareToIgnoreCase(s1)>=0) {
//            logger.info("ook");
//        }
        Format f = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, 30);
        Date tomorrow = c.getTime();
        System.out.println(f.format(tomorrow));
    }
}
