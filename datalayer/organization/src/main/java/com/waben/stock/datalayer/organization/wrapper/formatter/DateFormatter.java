package com.waben.stock.datalayer.organization.wrapper.formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

public class DateFormatter implements Formatter<Date> {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public Date parse(String text, Locale locale) throws ParseException {
            return formatter.parse(text);
        }

        @Override
        public String print(Date date, Locale locale) {
            return formatter.format(date);
        }
    }