package com.commonutils.util.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public final class SysDate {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String STANDARD_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String DEFAULT_EXP_DATE = "2049-12-31 23:59:59";

    public static final String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";

    public static final String STANDARD_FORMAT_YYYYMMDDHH = "yyyyMMddHH";

    public static String getDateTimeStream() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(STANDARD_FORMAT_YYYYMMDDHHMMSS);
        return dtf.format(now);
    }

    public static String prevDateByFormat(String time, String format, String node, long unit) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime ldt = null;
        if(format.contains("H") || format.contains("m") || format.contains("s")) {
            ldt = LocalDateTime.parse(time, dtf);
        } else {
            ldt = LocalDate.parse(time, dtf).atStartOfDay();
        }
        if("y".equals(node)) {
            return dtf.format(ldt.minusYears(unit));
        } else if ("M".equals(node)) {
            return dtf.format(ldt.minusMonths(unit));
        } else if ("d".equals(node)) {
            return dtf.format(ldt.minusDays(unit));
        } else if ("H".equals(node)) {
            return dtf.format(ldt.minusHours(unit));
        } else if ("m".equals(node)) {
            return dtf.format(ldt.minusMinutes(unit));
        } else if ("s".equals(node)) {
            return dtf.format(ldt.minusSeconds(unit));
        } else {
            return time;
        }
    }

    public static int dateComp(String srcTime, String destTime, String format) {
        if(srcTime.equals(destTime)) {
            return 0;
        } else {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
            LocalDateTime srcDate = null;
            LocalDateTime destDate = null;
            if(format.contains("H") || format.contains("m") || format.contains("s")) {
                srcDate = LocalDateTime.parse(srcTime, dtf);
                destDate = LocalDateTime.parse(destTime, dtf);
            } else {
                srcDate = LocalDate.parse(srcTime, dtf).atStartOfDay();
                destDate = LocalDate.parse(destTime, dtf).atStartOfDay();
            }
            if(srcDate.isAfter(destDate)) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public static String getCurrentTime(SimpleDateFormat sdf) {
        Date date = Calendar.getInstance().getTime();
        return sdf.format(date);
    }

    public static Timestamp getSysDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_FORMAT);
            return new Timestamp(sdf.parse(dateStr).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Timestamp getSysDate(String dateStr, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            return new Timestamp(df.parse(dateStr).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFormatDate(String dateStr, SimpleDateFormat df) {
        try {
            return df.format(new Timestamp(df.parse(dateStr).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFormatDate(Date date, SimpleDateFormat df) {
        try {
            return df.format(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Timestamp getSysDate() {
        Date date = Calendar.getInstance().getTime();
        return getFormatSimpleDate(new Timestamp(date.getTime()));
    }

    public static Timestamp getFormatSimpleDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(STANDARD_FORMAT);
        String dateT = df.format(date);
        try {
            return new Timestamp(df.parse(dateT).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentTime() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_FORMAT_YYYYMMDDHHMMSS);
        return sdf.format(date);
    }

    public static String getStandardFormatCurrentTime() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_FORMAT);
        return sdf.format(date);
    }

    public static String convertDateFormat(String dateStr, String srcFmt, String destFmt) {
        DateTimeFormatter srcFmtObj = DateTimeFormatter.ofPattern(srcFmt);
        DateTimeFormatter destFmtObj = DateTimeFormatter.ofPattern(destFmt);
        LocalDateTime date = LocalDateTime.parse(dateStr, srcFmtObj);
        return destFmtObj.format(date);
    }
}
