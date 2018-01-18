package com.waben.stock.risk;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarTest {

    public static void main(String[] args) {
//        Calendar c= Calendar.getInstance();
//        c.setTime(new Date());
//        long timeInMillis = c.getTimeInMillis()/(1000 * 60 * 60 * 24);
//        System.out.println(timeInMillis);
//        c.add(5,1);
//        timeInMillis = c.getTimeInMillis()/(1000 * 60 * 60 * 24);
//        System.out.println(timeInMillis);
        Date date = new Date();
        DateFormat df = DateFormat.getTimeInstance();//只显示出时分秒
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        String format = df.format(date);
        Date currentTime = null;
        Date expriessTime = null;
        try {
            currentTime = sdf.parse("14:40:00");
            expriessTime = sdf.parse("14:40:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(currentTime.getTime()>=expriessTime.getTime()) {
            System.out.println(currentTime.getTime()+">="+expriessTime.getTime());
        }
        System.out.println(currentTime);
        System.out.println(expriessTime);
    }
}
