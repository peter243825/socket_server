package com.example.server.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Log {
    private static final DateTimeFormatter sTimeFormatter = DateTimeFormat.forPattern("[yyyy-MM-dd HH:mm:ss.ms]");

    public static void log(final String str) {
        final DateTime currentTime = new DateTime(DateTimeZone.getDefault());
        System.out.println(sTimeFormatter.print(currentTime) + "\t" + str);
    }
}
