package com.unicorn.taskscan.utils;

import com.unicorn.taskscan.record.Record;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

public class DateUtils {

    public static String getDateString(long time){
        final String format = "yyyy-MM-dd HH:mm:ss:SSS";
        return new DateTime(time).toString(format);
    }

    public static String getDiff(Record record) {
        Interval interval = new Interval(record.getDepartTime(), record.getArriveTime());
        Period period = interval.toPeriod();
        String diff = "";
        int hour = period.getHours();
        if (hour != 0) {
            diff += (hour + "小时");
        }
        int minute = period.getMinutes();
        if (minute != 0) {
            diff += (minute + "分钟");
        }
        int second = period.getSeconds();
        if (second != 0) {
            diff += (second + "秒");
        }
        return diff;
    }

}
