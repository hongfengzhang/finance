package com.waben.stock.risk;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarTest {
    static Logger logger = LoggerFactory.getLogger(CalendarTest.class);
    public static void main(String[] args) throws ParseException, InterruptedException {
        // 止盈点位价格 = 买入价格 + ((市值 * 止盈点)/股数)

        // 止损点位价格 = 买入价格 - ((市值 * 止损点)/股数)

        // 止盈预警点位价格 = (止盈点位 - 买入点位) * 90% + 买入点位

        // 止损预警点位价格 = 买入点位 - (买入点位 - 止损点位) * 90%


    }
}
