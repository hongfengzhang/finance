package com.waben.stock.datalayer.buyrecord.warpper.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter implements Formatter<Date> {

        private static final ThreadLocal<SimpleDateFormat> messageFormat = new ThreadLocal<>();
        private static final String MESSAGE_FORMAT = "yyyy-MM-dd HH:mm:ss";

        private static final SimpleDateFormat getDateFormat() {
            SimpleDateFormat format = messageFormat.get();
            if (null == format) {
                format = new SimpleDateFormat(MESSAGE_FORMAT, Locale.getDefault());
                messageFormat.set(format);
            }
            return format;
        }

        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public Date parse(String text, Locale locale) throws ParseException {
            return getDateFormat().parse(text);
        }

        @Override
        public String print(Date date, Locale locale) {
            return getDateFormat().format(date);
        }
    }