package com.waben.stock.risk;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class CalendarTest {

    public static void main(String[] args) {
        Calendar c= Calendar.getInstance();
        c.setTime(new Date());
        long timeInMillis = c.getTimeInMillis()/(1000 * 60 * 60 * 24);
        System.out.println(timeInMillis);
        c.add(5,1);
        timeInMillis = c.getTimeInMillis()/(1000 * 60 * 60 * 24);
        System.out.println(timeInMillis);
    }
}
