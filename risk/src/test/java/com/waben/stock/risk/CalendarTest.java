package com.waben.stock.risk;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class CalendarTest {

    public static void main(String[] args) {
        Calendar c= Calendar.getInstance();
        c.setTime(new Date());
        int tradeDay = c.get(Calendar.DAY_OF_YEAR);
        System.out.println(tradeDay);
    }
}
