package com.mistra.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @ Author: WangRui
 * @ Version: 1.0
 * @ Time: 2019-05-09 18:07
 * @ Description:
 */
public class DateUtils {

    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 用来全局控制 上一周，本周，下一周的周数变化
     */
    private static int weeks = 0;
    /**
     * 一月最大天数
     */
    private static int maxDate;
    /**
     * 一年最大天数
     */
    private static int maxYear;
    /**
     * 时间格式化字符串
     */
    private static String yearFormatString = "yyyy-MM-dd";

    public static void main(String[] args) {
        logger.info("获取当天日期:{}", DateUtils.getNowTime(yearFormatString));
        logger.info("获取本周一日期:{}", DateUtils.getMondayOFWeek());
        logger.info("获取本周日的日期~:{}", DateUtils.getCurrentWeekday());
        logger.info("获取上周一日期:{}", DateUtils.getPreviousWeekday());
        logger.info("获取上周日日期:{}", DateUtils.getPreviousWeekSunday());
        logger.info("获取下周一日期:{}", DateUtils.getNextMonday());
        logger.info("获取下周日日期:{}", DateUtils.getNextSunday());
        logger.info("获取本月第一天日期:{}", DateUtils.getFirstDayOfMonth());
        logger.info("获取本月最后一天日期:{}", DateUtils.getDefaultDay());
        logger.info("获取上月第一天日期:{}", DateUtils.getPreviousMonthFirst());
        logger.info("获取上月最后一天的日期:{}", DateUtils.getPreviousMonthEnd());
        logger.info("获取下月第一天日期:{}", DateUtils.getNextMonthFirst());
        logger.info("获取下月最后一天日期:{}", DateUtils.getNextMonthEnd());
        logger.info("获取本年的第一天日期:{}", DateUtils.getCurrentYearFirst());
        logger.info("获取本年最后一天日期:{}", DateUtils.getCurrentYearEnd());
        logger.info("获取去年的第一天日期:{}", DateUtils.getPreviousYearFirst());
        logger.info("获取去年的最后一天日期:{}", DateUtils.getPreviousYearEnd());
        logger.info("获取明年第一天日期:{}", DateUtils.getNextYearFirst());
        logger.info("获取明年最后一天日期:{}", DateUtils.getNextYearEnd());
        logger.info("获取本季度第一天:{}", DateUtils.getThisSeasonFirstTime(11));
        logger.info("获取本季度最后一天:{}", DateUtils.getThisSeasonFinallyTime(11));
        logger.info("获取两个日期之间间隔天数2008-12-1~2008-29:{}", DateUtils.getTwoDay("2008-12-1", "2008-9-29"));
        logger.info("获取当前月的第几周：{}", DateUtils.getWeekOfMonth());
        logger.info("获取当前年份：{}", DateUtils.getYear());
        logger.info("获取当前月份：{}", DateUtils.getMonth());
        logger.info("获取今天在本年的第几天：{}", DateUtils.getDayOfYear());
        logger.info("获得今天在本月的第几天(获得当前日)：{}", DateUtils.getDayOfMonth());
        logger.info("获得今天在本周的第几天：{}", DateUtils.getDayOfWeek());
    }

    /**
     * 获得当前年份
     *
     * @return int
     */
    private static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获得当前月份
     *
     * @return int
     */
    private static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 获得今天在本年的第几天
     *
     * @return int
     */
    private static int getDayOfYear() {
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获得今天在本月的第几天(获得当前日)
     *
     * @return int
     */
    private static int getDayOfMonth() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得今天在本周的第几天
     *
     * @return int
     */
    private static int getDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获得今天是这个月的第几周
     *
     * @return int
     */
    private static int getWeekOfMonth() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    /**
     * 获得半年后的日期
     *
     * @return Date
     */
    private static Date getTimeYearNext() {
        Calendar.getInstance().add(Calendar.DAY_OF_YEAR, 183);
        return Calendar.getInstance().getTime();
    }

    /**
     * 将日期转换成字符串
     *
     * @param dateTime Date
     * @return String
     */
    private static String convertDateToString(Date dateTime) {
        SimpleDateFormat df = new SimpleDateFormat(yearFormatString);
        return df.format(dateTime);
    }

    /**
     * 得到二个日期间的间隔天数
     *
     * @param time1 String
     * @param time2 String
     * @return String
     */
    private static String getTwoDay(String time1, String time2) {
        SimpleDateFormat myFormaDateUtilser = new SimpleDateFormat(yearFormatString);
        long day = 0;
        try {
            java.util.Date date = myFormaDateUtilser.parse(time1);
            java.util.Date mydate = myFormaDateUtilser.parse(time2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate String
     * @return String
     */
    private static String getWeek(String sdate) {
        // 再转换为时间
        Date date = DateUtils.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate String
     * @return Date
     */
    private static Date strToDate(String strDate) {
        SimpleDateFormat formaDateUtilser = new SimpleDateFormat(yearFormatString);
        ParsePosition pos = new ParsePosition(0);
        return formaDateUtilser.parse(strDate, pos);
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1 String
     * @param date2 String
     * @return long
     */
    private static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormaDateUtilser = new SimpleDateFormat(yearFormatString);
        java.util.Date date;
        java.util.Date myDate;
        try {
            date = myFormaDateUtilser.parse(date1);
            myDate = myFormaDateUtilser.parse(date2);
        } catch (Exception e) {
            date = new Date();
            myDate = new Date();
        }
        return (date.getTime() - myDate.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 计算当月最后一天,返回字符串
     *
     * @return String
     */
    private static String getDefaultDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(yearFormatString);
        Calendar lastDate = Calendar.getInstance();
        // 设为当前月的1号
        lastDate.set(Calendar.DATE, 1);
        // 加一个月，变为下月的1号
        lastDate.add(Calendar.MONTH, 1);
        // 减去一天，变为当月最后一天
        lastDate.add(Calendar.DATE, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 上月第一天
     *
     * @return String
     */
    private static String getPreviousMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(yearFormatString);
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
        // lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获取当月第一天
     *
     * @return String
     */
    private static String getFirstDayOfMonth() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(yearFormatString);
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得本周星期日的日期
     *
     * @return String
     */
    private static String getCurrentWeekday() {
        weeks = 0;
        int mondayPlus = DateUtils.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        return df.format(monday);
    }

    /**
     * 获取当天时间
     *
     * @param dateFormat String
     * @return String
     */
    private static String getNowTime(String dateFormat) {
        Date now = new Date();
        // 可以方便地修改日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(now);
    }

    /**
     * 获得当前日期与本周日相差的天数
     *
     * @return int
     */
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        // 因为按中国礼拜一作为第一天所以这里减1
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    /**
     * 获得本周一的日期
     *
     * @return String
     */
    private static String getMondayOFWeek() {
        weeks = 0;
        int mondayPlus = DateUtils.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        return df.format(monday);
    }

    /**
     * 获得相应周的周六的日期
     *
     * @return String
     */
    private static String getSaturday() {
        int mondayPlus = DateUtils.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        return df.format(monday);
    }

    /**
     * 获得上周星期日的日期
     *
     * @return String
     */
    private static String getPreviousWeekSunday() {
        weeks = 0;
        weeks--;
        int mondayPlus = DateUtils.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        return df.format(monday);
    }

    /**
     * 获得上周星期一的日期
     *
     * @return String
     */
    private static String getPreviousWeekday() {
        weeks--;
        int mondayPlus = DateUtils.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        return df.format(monday);
    }

    /**
     * 获得下周星期一的日期
     *
     * @return String
     */
    private static String getNextMonday() {
        weeks++;
        int mondayPlus = DateUtils.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        return df.format(monday);
    }

    /**
     * 获得下周星期日的日期
     *
     * @return String
     */
    private static String getNextSunday() {
        int mondayPlus = DateUtils.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        return df.format(monday);
    }

    private static int getMonthPlus() {
        Calendar cd = Calendar.getInstance();
        int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
        cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        maxDate = cd.get(Calendar.DATE);
        if (monthOfNumber == 1) {
            return -maxDate;
        } else {
            return 1 - monthOfNumber;
        }
    }

    /**
     * 获得上月最后一天的日期
     *
     * @return String
     */
    private static String getPreviousMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(yearFormatString);
        Calendar lastDate = Calendar.getInstance();
        // 减一个月
        lastDate.add(Calendar.MONTH, -1);
        // 把日期设置为当月第一天
        lastDate.set(Calendar.DATE, 1);
        // 日期回滚一天，也就是本月最后一天
        lastDate.roll(Calendar.DATE, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得下个月第一天的日期
     *
     * @return String
     */
    private static String getNextMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(yearFormatString);
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得下个月最后一天的日期
     *
     * @return String
     */
    private static String getNextMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(yearFormatString);
        Calendar lastDate = Calendar.getInstance();
        // 加一个月
        lastDate.add(Calendar.MONTH, 1);
        // 把日期设置为当月第一天
        lastDate.set(Calendar.DATE, 1);
        // 日期回滚一天，也就是本月最后一天
        lastDate.roll(Calendar.DATE, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得明年最后一天的日期
     *
     * @return String
     */
    private static String getNextYearEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(yearFormatString);
        Calendar lastDate = Calendar.getInstance();
        // 加一个年
        lastDate.add(Calendar.YEAR, 1);
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        lastDate.roll(Calendar.DAY_OF_YEAR, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得明年第一天的日期
     *
     * @return String
     */
    private static String getNextYearFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(yearFormatString);
        Calendar lastDate = Calendar.getInstance();
        // 加一个年
        lastDate.add(Calendar.YEAR, 1);
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        str = sdf.format(lastDate.getTime());
        return str;

    }

    /**
     * 获得本年有多少天
     *
     * @return int
     */
    private static int getMaxYear() {
        Calendar cd = Calendar.getInstance();
        // 把日期设为当年第一天
        cd.set(Calendar.DAY_OF_YEAR, 1);
        // 把日期回滚一天。
        cd.roll(Calendar.DAY_OF_YEAR, -1);
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        return MaxYear;
    }

    private static int getYearPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得当天是一年中的第几天
        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);
        // 把日期设为当年第一天
        cd.set(Calendar.DAY_OF_YEAR, 1);
        // 把日期回滚一天。
        cd.roll(Calendar.DAY_OF_YEAR, -1);
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        if (yearOfNumber == 1) {
            return -MaxYear;
        } else {
            return 1 - yearOfNumber;
        }
    }

    /**
     * 获得本年第一天的日期
     *
     * @return String
     */
    private static String getCurrentYearFirst() {
        int yearPlus = DateUtils.getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus);
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        return df.format(yearDay);
    }

    /**
     * 获得本年最后一天的日期
     *
     * @return String
     */
    private static String getCurrentYearEnd() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String years = dateFormat.format(date);
        return years + "-12-31";
    }

    /**
     * 获得上年第一天的日期
     *
     * @return String
     */
    private static String getPreviousYearFirst() {
        int year = DateUtils.getYear();
        year--;
        return year + "-1-1";
    }

    /**
     * 获得上年最后一天的日期
     *
     * @return String
     */
    private static String getPreviousYearEnd() {
        weeks--;
        int yearPlus = DateUtils.getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus + maxYear * weeks + (maxYear - 1));
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        return df.format(yearDay);
    }

    /**
     * 获得本季度第一天
     *
     * @param month int
     * @return String
     */
    private static String getThisSeasonFirstTime(int month) {
        int array[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        int season = DateUtils.getSeason(month);
        return DateUtils.getYear() + "-" + array[season - 1][0] + "-" + 1;

    }

    /**
     * 获得季度
     *
     * @param month 月份
     * @return int 季度
     */
    private static int getSeason(int month) {
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        return season;
    }

    /**
     * 获得本季度最后一天
     *
     * @param month int
     * @return String
     */
    private static String getThisSeasonFinallyTime(int month) {
        int array[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        int season = DateUtils.getSeason(month);
        int end_month = array[season - 1][2];
        int years_value = DateUtils.getYear();
        int end_days = getLastDayOfMonth(years_value, end_month);
        return years_value + "-" + end_month + "-" + end_days;
    }

    /**
     * 获取某年某月的最后一天
     *
     * @param year  年
     * @param month 月
     * @return int 最后一天
     */
    private static int getLastDayOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    /**
     * 是否是闰年
     *
     * @param year int
     * @return boolean
     */
    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
