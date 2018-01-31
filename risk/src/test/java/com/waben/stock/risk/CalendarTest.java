package com.waben.stock.risk;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarTest {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss");
        Date parse = sdfTime.parse("10:40:00");
//        Date parse1 = sdf.parse("2018-01-25 00:00:00");
//        Date parse2 = sdf2.parse("2018-01-27 13:59:59");
        DateFormat df = DateFormat.getTimeInstance();
        Date parse3 = sdf2.parse("2018-01-26 23:59:59");

        Date parse1 = sdfTime.parse(df.format(new Date()));
        String format = sdf2.format(new Date());
        String format1 = sdf2.format(parse3);

        System.out.println(format.equalsIgnoreCase(format1));
        System.out.println(format.compareToIgnoreCase(format1));
        if((format.equalsIgnoreCase(format1)||format.compareToIgnoreCase(format1)>0)&&parse1.getTime()>parse.getTime()) {
            System.out.println("ssssss");
        }
//        new Date().getTime() <
//
//        Calendar c= Calendar.getInstance();
//        c.setTime(new Date());
//        long timeInMillis = c.getTimeInMillis()/(1000 * 60 * 60 * 24);
//        System.out.println(timeInMillis);
//        System.out.println(c.getTimeInMillis());
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
//        Date parse = sdf.parse("2018-01-25 08:00:00");
//        System.out.println(parse.getTime());
//        timeInMillis = parse.getTime()/(1000 * 60 * 60 * 24);
//        System.out.println(timeInMillis);
//        Date date = new Date();
//        DateFormat df = DateFormat.getTimeInstance();//只显示出时分秒
//        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
//        String format = df.format(date);
//        Date currentTime = null;
//        Date expriessTime = null;
//        try {
//            currentTime = sdf.parse("14:40:00");
//            expriessTime = sdf.parse("14:40:00");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if(currentTime.getTime()>=expriessTime.getTime()) {
//            System.out.println(currentTime.getTime()+">="+expriessTime.getTime());
//        }
//        System.out.println(currentTime);
//        System.out.println(expriessTime);
    }
}
