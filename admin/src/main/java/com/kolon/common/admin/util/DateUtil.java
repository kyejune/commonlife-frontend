package com.kolon.common.admin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
    public static String getDateTimeByPattern(String pattern)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.KOREAN);

        String dateString = formatter.format(new Date());

        return dateString;
    }

    public static String getDateFormat(String dateTime, String pattern)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(dateTime, pattern);
        if (stringtokenizer.countTokens() > 1) {
            return dateTime.toString();
        }
        StringBuffer sbDate = new StringBuffer("");
        if ((dateTime != null) && (dateTime.length() >= 8))
        {
            sbDate.append(dateTime.substring(0, 4));
            sbDate.append(pattern);
            sbDate.append(dateTime.substring(4, 6));
            sbDate.append(pattern);
            sbDate.append(dateTime.substring(6, 8));
        }
        return sbDate.toString();
    }

    public static String getDateFormatYM(String dateTime, String pattern)
    {
        StringBuffer sbDate = new StringBuffer("");
        if ((dateTime != null) && (dateTime.length() >= 6))
        {
            sbDate.append(dateTime.substring(0, 4));
            sbDate.append(pattern);
            sbDate.append(dateTime.substring(4, 6));
        }
        return sbDate.toString();
    }

    public static String getDateFormatYM1(String dateTime, String pattern)
    {
        StringBuffer sbDate = new StringBuffer("");
        if ((dateTime != null) && (dateTime.length() >= 6))
        {
            sbDate.append(dateTime.substring(0, 4));
            sbDate.append(pattern);
            sbDate.append(dateTime.substring(4, 7));
        }
        return sbDate.toString();
    }

    public static String getDateFormatYear(String dateTime)
    {
        StringBuffer sbDate = new StringBuffer("");
        if ((dateTime != null) && (dateTime.length() >= 4)) {
            sbDate.append(dateTime.substring(0, 4));
        }
        return sbDate.toString();
    }

    public static String getDateFormatMonth(String dateTime)
    {
        StringBuffer sbDate = new StringBuffer("");
        if ((dateTime != null) && (dateTime.length() >= 7)) {
            sbDate.append(dateTime.substring(5, 7));
        }
        return sbDate.toString();
    }

    public static String getDeleteFormatYMD(String data)
    {
        StringBuffer sb = new StringBuffer();
        if ((data == null) || (data.equals(""))) {
            return "";
        }
        sb.append(data.substring(0, 4));
        sb.append(data.substring(5, 7));
        sb.append(data.substring(8));

        return sb.toString();
    }

    public static String getTimeFormat(String dateTime, String pattern)
    {
        StringBuffer sbDate = new StringBuffer("");
        if ((dateTime != null) && (dateTime.length() >= 4))
        {
            sbDate.append(dateTime.substring(0, 2));
            sbDate.append(pattern);
            sbDate.append(dateTime.substring(2, 4));
        }
        if ((dateTime != null) && (dateTime.length() == 6))
        {
            sbDate.append(pattern);
            sbDate.append(dateTime.substring(4, 6));
        }
        return sbDate.toString();
    }

    public static String getDateTime(String dateTime, String pattern)
    {
        StringBuffer sbDate = new StringBuffer("");
        if ((dateTime != null) && (dateTime.length() >= 12))
        {
            sbDate.append(dateTime.substring(2, 4));
            sbDate.append(pattern);
            sbDate.append(dateTime.substring(4, 6));
            sbDate.append(pattern);
            sbDate.append(dateTime.substring(6, 8));
            sbDate.append(" ");
            sbDate.append(dateTime.substring(8, 10));
            sbDate.append(":");
            sbDate.append(dateTime.substring(10, 12));
        }
        if ((dateTime != null) && (dateTime.length() == 14))
        {
            sbDate.append(":");
            sbDate.append(dateTime.substring(12, 14));
        }
        return sbDate.toString();
    }

    public static String getDateTime2(String dateTime, String pattern)
    {
        StringBuffer sbDate = new StringBuffer("");
        if ((dateTime != null) && (dateTime.length() >= 10))
        {
            sbDate.append(dateTime.substring(0, 4));
            sbDate.append(pattern);
            sbDate.append(dateTime.substring(4, 6));
            sbDate.append(pattern);
            sbDate.append(dateTime.substring(6, 8));
            sbDate.append(" ");
            sbDate.append(dateTime.substring(8, 10));
        }
        return sbDate.toString();
    }

    public static String getToday()
    {
        String msgDate = getDateTimeByPattern("yyyyMMdd");
        return msgDate;
    }

    public static String getToday2()
    {
        String msgDate = getDateTimeByPattern("yyyy-MM-dd");
        return msgDate;
    }

    public static String getYesterday()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        String y_year = Integer.toString(cal.get(Calendar.YEAR));
        String y_month = Integer.toString(cal.get(Calendar.MONTH)+1);
        String y_date = Integer.toString(cal.get(Calendar.DATE));

        if(y_month.length() ==1 ) y_month = "0" +y_month; //month 3 => 03
        if(y_date.length() ==1 ) y_date = "0" +y_date;  //day 7=> 07

        String yesterday = y_year + y_month + y_date;

        return yesterday;
    }

    public static String getYesterday2()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        String y_year = Integer.toString(cal.get(Calendar.YEAR));
        String y_month = Integer.toString(cal.get(Calendar.MONTH)+1);
        String y_date = Integer.toString(cal.get(Calendar.DATE));

        if(y_month.length() ==1 ) y_month = "0" +y_month; //month 3 => 03
        if(y_date.length() ==1 ) y_date = "0" +y_date;  //day 7=> 07

        String yesterday = y_year +'-'+ y_month +'-'+ y_date;

        return yesterday;
    }

    public static String getThisTime()
    {
        String msgTime = "";

        SimpleDateFormat simpleDate = new SimpleDateFormat("HHmmss");
        msgTime = simpleDate.format(new Date());
        return msgTime;
    }

    public static String getDateTime()
    {
        String msgDate = "";

        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        msgDate = simpleDate.format(new Date());
        return msgDate;
    }

    public static String compareDate(String date1, String date2)
    {
        if ((date1 != null) && (date1.length() >= 8) && (date2 != null) && (date2.length() >= 8)) {
            try
            {
                int year1 = Integer.parseInt(date1.substring(0, 4));
                int month1 = 0;
                if (date1.substring(4, 5).equals("0")) {
                    month1 = Integer.parseInt(date1.substring(5, 6));
                } else {
                    month1 = Integer.parseInt(date1.substring(4, 6));
                }
                month1 -= 1;
                int dayOfMonth1 = 1;
                if (date1.substring(6, 7).equals("0")) {
                    dayOfMonth1 = Integer.parseInt(date1.substring(7, 8));
                } else {
                    dayOfMonth1 = Integer.parseInt(date1.substring(6, 8));
                }
                int year2 = Integer.parseInt(date2.substring(0, 4));
                int month2 = 0;
                if (date2.substring(4, 5).equals("0")) {
                    month2 = Integer.parseInt(date2.substring(5, 6));
                } else {
                    month2 = Integer.parseInt(date2.substring(4, 6));
                }
                month2 -= 1;
                int dayOfMonth2 = 1;
                if (date2.substring(6, 7).equals("0")) {
                    dayOfMonth2 = Integer.parseInt(date2.substring(7, 8));
                } else {
                    dayOfMonth2 = Integer.parseInt(date2.substring(6, 8));
                }
                Calendar cal1 = new GregorianCalendar(year1, month1, dayOfMonth1);
                Calendar cal2 = new GregorianCalendar(year2, month2, dayOfMonth2);

                return Integer.toString(cal1.compareTo(cal2));
            }
            catch (Exception e)
            {
                return "";
            }
        }
        return "";
    }

    public static int getDayDifference(String startDate, String endDate)
    {
        if (startDate.length() != 8) {
            return 0;
        }
        startDate = startDate + "000000";
        endDate = endDate + "000000";

        long diffMillis = 0L;
        int diff = 0;
        try
        {
            Date endDay = new SimpleDateFormat("yyyyMMddHHmmss").parse(endDate);

            Calendar endDayCal = new GregorianCalendar();
            endDayCal.setTime(endDay);

            Date startDay = new SimpleDateFormat("yyyyMMddHHmmss").parse(startDate);
            Calendar startDayCal = new GregorianCalendar();
            startDayCal.setTime(startDay);

            diffMillis = endDayCal.getTimeInMillis() - startDayCal.getTimeInMillis();

            diff = (int)(diffMillis / 86400000L);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return diff;
    }

    public static int getMonDifference(String startMon, String endMon)
    {
        int strtYear = Integer.parseInt(startMon.substring(0, 4));
        int strtMonth = Integer.parseInt(startMon.substring(4, 6));

        int endYear = Integer.parseInt(endMon.substring(0, 4));
        int endMonth = Integer.parseInt(endMon.substring(4, 6));

        int diff = (endYear - strtYear) * 12 + (endMonth - strtMonth);

        return diff;
    }

    public static String getDatePlusDay(String YYYYMMDD, String day)
    {
        int iYear = Integer.parseInt(YYYYMMDD.substring(0, 4));
        int iMonth = Integer.parseInt(YYYYMMDD.substring(4, 6)) - 1;
        int iDays = Integer.parseInt(YYYYMMDD.substring(6, 8));

        int iDD = Integer.parseInt(day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cGivenDay = new GregorianCalendar(iYear, iMonth, iDays);

        String sGivenDay = sdf.format(cGivenDay.getTime());

        cGivenDay.add(5, iDD);

        String sDayAfter = sdf.format(cGivenDay.getTime());

        return sDayAfter;
    }

    public static String getDatePlusMonth(String YYYYMMDD, String month)
    {
        int iYear = Integer.parseInt(YYYYMMDD.substring(0, 4));
        int iMonth = Integer.parseInt(YYYYMMDD.substring(4, 6)) - 1;
        int iDays = Integer.parseInt(YYYYMMDD.substring(6, 8));

        int iMM = Integer.parseInt(month);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cGivenDay = new GregorianCalendar(iYear, iMonth, iDays);

        String sGivenDay = sdf.format(cGivenDay.getTime());

        cGivenDay.add(2, iMM);

        String sDayAfter = sdf.format(cGivenDay.getTime());

        return sDayAfter;
    }

    public static long getTimeToTimeSecond(String date1, String min)
    {
        int iYear = Integer.parseInt(date1.substring(0, 4));
        int iMonth = Integer.parseInt(date1.substring(4, 6)) - 1;
        int iDays = Integer.parseInt(date1.substring(6, 8));
        int iHour = Integer.parseInt(date1.substring(8, 10));
        int iMin = Integer.parseInt(date1.substring(10, 12));
        int iSec = Integer.parseInt(date1.substring(12, 14));

        Calendar first = new GregorianCalendar(iYear, iMonth, iDays, iHour, iMin, iSec);

        long lFirst = first.getTimeInMillis();

        long lSecond = System.currentTimeMillis();

        long lTime = (lSecond - lFirst) / 1000L;

        long lMin = Long.parseLong(min) * 60L;

        long lTarget = lMin - lTime;
        return lTarget;
    }

    public static long getTimeAndTimeToSecond(String date1, String date2)
    {
        int iYear = Integer.parseInt(date1.substring(0, 4));
        int iMonth = Integer.parseInt(date1.substring(4, 6)) - 1;
        int iDays = Integer.parseInt(date1.substring(6, 8));
        int iHour = Integer.parseInt(date1.substring(8, 10));
        int iMin = Integer.parseInt(date1.substring(10, 12));
        int iSec = Integer.parseInt(date1.substring(12, 14));

        Calendar first = new GregorianCalendar(iYear, iMonth, iDays, iHour, iMin, iSec);

        long lFirst = first.getTimeInMillis();

        iYear = Integer.parseInt(date2.substring(0, 4));
        iMonth = Integer.parseInt(date2.substring(4, 6)) - 1;
        iDays = Integer.parseInt(date2.substring(6, 8));
        iHour = Integer.parseInt(date2.substring(8, 10));
        iMin = Integer.parseInt(date2.substring(10, 12));
        iSec = Integer.parseInt(date2.substring(12, 14));

        Calendar second = new GregorianCalendar(iYear, iMonth, iDays, iHour, iMin, iSec);

        long lSecond = second.getTimeInMillis();

        long lTime = (lSecond - lFirst) / 1000L;

        return lTime;
    }

    public static long getTimeAndTimeToDay(String date1, String date2)
    {
        int iYear = Integer.parseInt(date1.substring(0, 4));
        int iMonth = Integer.parseInt(date1.substring(4, 6)) - 1;
        int iDays = Integer.parseInt(date1.substring(6, 8));
        int iHour = Integer.parseInt(date1.substring(8, 10));
        int iMin = Integer.parseInt(date1.substring(10, 12));
        int iSec = Integer.parseInt(date1.substring(12, 14));

        Calendar first = new GregorianCalendar(iYear, iMonth, iDays, iHour, iMin, iSec);

        long lFirst = first.getTimeInMillis();

        iYear = Integer.parseInt(date2.substring(0, 4));
        iMonth = Integer.parseInt(date2.substring(4, 6)) - 1;
        iDays = Integer.parseInt(date2.substring(6, 8));
        iHour = Integer.parseInt(date2.substring(8, 10));
        iMin = Integer.parseInt(date2.substring(10, 12));
        iSec = Integer.parseInt(date2.substring(12, 14));

        Calendar second = new GregorianCalendar(iYear, iMonth, iDays, iHour, iMin, iSec);

        long lSecond = second.getTimeInMillis();

        long lDay = (lSecond - lFirst) / 86400000L;

        return lDay;
    }

    public static long getSecToMin(long time)
    {
        long min = time / 60L;

        return min;
    }

    public static long getSecToMinOtherSec(long time)
    {
        long sec = time % 60L;

        return sec;
    }

    public static String getTimeData(String date)
    {
        String time = "00";
        if (date.length() >= 10) {
            time = date.substring(8, 10);
        }
        return time;
    }

    public static String getMinData(String date)
    {
        String min = "00";
        if (date.length() >= 12)
        {
            String tempMin = date.substring(10, 12);
            int nMin = Integer.parseInt(tempMin);
            if ((nMin >= 0) && (nMin < 10)) {
                min = "00";
            } else if ((nMin >= 10) && (nMin < 20)) {
                min = "10";
            } else if ((nMin >= 20) && (nMin < 30)) {
                min = "20";
            } else if ((nMin >= 30) && (nMin < 40)) {
                min = "30";
            } else if ((nMin >= 40) && (nMin < 50)) {
                min = "40";
            } else if ((nMin >= 50) && (nMin < 60)) {
                min = "50";
            }
        }
        return min;
    }

    public static String getWeekStartDay(String chooseDate)
    {
        Calendar sDate = getDateCalendarType(chooseDate);

        int weekVal = sDate.get(7);

        int addCnt = -1 * (weekVal - 1);
        sDate.add(5, addCnt);

        return getDateStringType(sDate);
    }

    public static String getWeekEndDay(String chooseDate)
    {
        Calendar sDate = getDateCalendarType(chooseDate);

        int weekVal = sDate.get(7);

        int addCnt = 1 * (7 - weekVal);
        sDate.add(5, addCnt);

        return getDateStringType(sDate);
    }

    public static String getNextWeekDay(String chooseDate)
    {
        Calendar sDate = getDateCalendarType(chooseDate);

        sDate.add(5, 7);

        return getDateStringType(sDate);
    }

    public static String getPrevWeekDay(String chooseDate)
    {
        Calendar sDate = getDateCalendarType(chooseDate);

        sDate.add(5, -7);

        return getDateStringType(sDate);
    }

    public static String getPrevNextMonthDay(String chooseDate, int month)
    {
        Calendar sDate = getDateCalendarType(chooseDate);

        sDate.add(Calendar.MONTH, month);

        return getDateStringType(sDate);
    }

    public static String getPrevNextDay(String chooseDate, int day)
    {
        Calendar sDate = getDateCalendarType(chooseDate);

        sDate.add(Calendar.DATE, day);

        return getDateStringType(sDate);
    }

    public static String getPrevNextYearDay(String chooseDate, int year)
    {
        Calendar sDate = getDateCalendarType(chooseDate);

        sDate.add(Calendar.YEAR, year);

        return getDateStringType(sDate);
    }



    public static String getPrevOneYearDay(String chooseDate)
    {
        Calendar sDate = getDateCalendarType(chooseDate);

        sDate.add(5, 65171);

        return getDateStringType(sDate);
    }

    public static Calendar getDateCalendarType(String dateStr)
    {
        Calendar calDate = new GregorianCalendar(Integer.parseInt(dateStr.substring(0, 4)), Integer.parseInt(dateStr.substring(4, 6)) - 1, Integer.parseInt(dateStr.substring(6, 8)));

        return calDate;
    }

    public static String getDateStringType(Calendar calDate)
    {
        String tmpDate = "";
        int iYear = calDate.get(1);
        tmpDate = tmpDate + Integer.toString(iYear);
        int iMonth = calDate.get(2) + 1;
        if (iMonth < 10) {
            tmpDate = tmpDate + "0";
        }
        tmpDate = tmpDate + Integer.toString(iMonth);
        int iDate = calDate.get(5);
        if (iDate < 10) {
            tmpDate = tmpDate + "0";
        }
        tmpDate = tmpDate + Integer.toString(iDate);

        return tmpDate;
    }

    public static String getMonthStringType(Calendar calDate)
    {
        String tmpDate = "";
        int iYear = calDate.get(1);
        tmpDate = tmpDate + Integer.toString(iYear);
        int iMonth = calDate.get(2) + 1;
        if (iMonth < 10) {
            tmpDate = tmpDate + "0";
        }
        tmpDate = tmpDate + Integer.toString(iMonth);

        return tmpDate;
    }

    public static String getChartDateFormat(String dateTime, String pattern)
    {
        StringBuffer sbDate = new StringBuffer("");
        if ((dateTime != null) && (dateTime.length() >= 4)) {
            sbDate.append(dateTime.substring(0, 4));
        }
        if ((dateTime != null) && (dateTime.length() >= 6))
        {
            sbDate.append(pattern);
            sbDate.append(dateTime.substring(4, 6));
        }
        if ((dateTime != null) && (dateTime.length() >= 8))
        {
            sbDate.append(pattern);
            sbDate.append(dateTime.substring(6, 8));
        }
        if ((dateTime != null) && (dateTime.length() >= 10))
        {
            sbDate.append(" ");
            sbDate.append(dateTime.substring(8, 10));
        }
        if ((dateTime != null) && (dateTime.length() >= 12))
        {
            sbDate.append(":");
            sbDate.append(dateTime.substring(10, 12));
        }
        return sbDate.toString();
    }

    public static String getSecondToFullTime(long total)
    {
        StringBuffer sbBuf = new StringBuffer("");
        if (total > 0L)
        {
            long nSec = total % 60L;
            long temp = total / 60L;
            if (temp <= 0L)
            {
                sbBuf.append(nSec);
                sbBuf.append("초");
                return sbBuf.toString();
            }
            long nMin = temp % 60L;
            long temp1 = temp / 60L;
            if (temp1 <= 0L)
            {
                sbBuf.append(nMin);
                sbBuf.append("분");
                sbBuf.append(nSec);
                sbBuf.append("초");
                return sbBuf.toString();
            }
            long nHour = temp1 % 24L;
            long nDay = temp1 / 24L;
            if (nHour <= 0L)
            {
                sbBuf.append(nHour);
                sbBuf.append("시간");
                sbBuf.append(nMin);
                sbBuf.append("분");
                sbBuf.append(nSec);
                sbBuf.append("초");
                return sbBuf.toString();
            }
            sbBuf.append(nDay);
            sbBuf.append("일");
            sbBuf.append(nHour);
            sbBuf.append("시간");
            sbBuf.append(nMin);
            sbBuf.append("분");
            sbBuf.append(nSec);
            sbBuf.append("초");
            return sbBuf.toString();
        }
        sbBuf.append("0");
        return sbBuf.toString();
    }

    public static String getPostgresDateFormat(String dateTime, String pattern)
    {
        StringBuffer sbDate = new StringBuffer("");
        if ((dateTime != null) && (dateTime.length() >= 8))
        {
            sbDate.append(dateTime.substring(0, 4));
            sbDate.append(pattern);
            sbDate.append(dateTime.substring(4, 6));
            sbDate.append(pattern);
            sbDate.append(dateTime.substring(6, 8));
        }
        if ((dateTime != null) && (dateTime.length() >= 10))
        {
            sbDate.append(" ");
            sbDate.append(dateTime.substring(8, 10));
        }
        else
        {
            sbDate.append(" 00");
        }
        if ((dateTime != null) && (dateTime.length() >= 12))
        {
            sbDate.append(":");
            sbDate.append(dateTime.substring(10, 12));
        }
        else
        {
            sbDate.append(" 00");
        }
        if ((dateTime != null) && (dateTime.length() >= 14))
        {
            sbDate.append(":");
            sbDate.append(dateTime.substring(12, 14));
        }
        else
        {
            sbDate.append(" 00");
        }
        return sbDate.toString();
    }

    public static String getTodayHan()
    {
        StringBuffer sbDate = new StringBuffer("");
        String today = getToday();
        sbDate.append(today.substring(0, 4));
        sbDate.append("년 ");
        sbDate.append(today.substring(4, 6));
        sbDate.append("월 ");
        sbDate.append(today.substring(6, 8));
        sbDate.append("일 ");
        return sbDate.toString();
    }

    public static String getDateTimeHan(String dateTime)
    {
        StringBuffer sbDate = new StringBuffer("");
        String today = dateTime;
        sbDate.append(today.substring(0, 4));
        sbDate.append("년 ");
        sbDate.append(today.substring(4, 6));
        sbDate.append("월 ");
        sbDate.append(today.substring(6, 8));
        sbDate.append("일 ");
        return sbDate.toString();
    }

    public static String getDateHyphe(String dateTime)
    {
        StringBuffer sbDate = new StringBuffer("");
        String today = dateTime;
        sbDate.append(today.substring(0, 4));
        sbDate.append("-");
        sbDate.append(today.substring(4, 6));
        sbDate.append("-");
        sbDate.append(today.substring(6, 8));

        return sbDate.toString();
    }

    public static String getDateUndenbar(String dateTime)
    {
        StringBuffer sbDate = new StringBuffer("");
        String today = dateTime;
        sbDate.append(today.substring(0, 4));
        sbDate.append("_");
        sbDate.append(today.substring(4, 6));
        sbDate.append("_");
        sbDate.append(today.substring(6, 8));

        return sbDate.toString();
    }

    public static String getTimeHanFormat(String time)
    {
        String timeString = "";
        if ((time != null) && (time.length() == 6))
        {
            int iHH = Integer.parseInt(time.substring(0, 2));
            int iMM = Integer.parseInt(time.substring(2, 4));
            int iDD = Integer.parseInt(time.substring(4, 6));
            SimpleDateFormat formatter = new SimpleDateFormat("a KK:mm");
            Calendar cGivenDay = new GregorianCalendar(0, 0, 0, iHH, iMM, iDD);

            timeString = formatter.format(cGivenDay.getTime());
        }
        return timeString;
    }

    public static String getWeekDay(String date)
    {
        String lang = "KOR";
        int cnt = 1;
        return getWeekDay(date, lang, cnt);
    }

    public static String getWeekDay(String date, String lang, int cnt)
    {
        if ((date == null) || (date.equals(""))) {
            return "";
        }
        if (date.length() > 7)
        {
            if (date.length() >= 10)
            {
                date = date.substring(0, 10);
                date = date.replace(".", "");
                date = date.replace("-", "");
            }
            int iYear = Integer.parseInt(date.substring(0, 4));
            int iMonth = Integer.parseInt(date.substring(4, 6)) - 1;
            int iDays = Integer.parseInt(date.substring(6, 8));
            Calendar cal = new GregorianCalendar(iYear, iMonth, iDays);
            int dayOfWeek = cal.get(7);
            String dayOfWeekStr = "";
            if (dayOfWeek == 1)
            {
                if ((lang.equals("KOR")) && (cnt == 1)) {
                    dayOfWeekStr = "일";
                } else {
                    dayOfWeekStr = "(Sun)";
                }
            }
            else if (dayOfWeek == 2)
            {
                if ((lang.equals("KOR")) && (cnt == 1)) {
                    dayOfWeekStr = "월";
                } else {
                    dayOfWeekStr = "(Mon)";
                }
            }
            else if (dayOfWeek == 3)
            {
                if ((lang.equals("KOR")) && (cnt == 1)) {
                    dayOfWeekStr = "화";
                } else {
                    dayOfWeekStr = "(Tue)";
                }
            }
            else if (dayOfWeek == 4)
            {
                if ((lang.equals("KOR")) && (cnt == 1)) {
                    dayOfWeekStr = "수";
                } else {
                    dayOfWeekStr = "(Wed)";
                }
            }
            else if (dayOfWeek == 5)
            {
                if ((lang.equals("KOR")) && (cnt == 1)) {
                    dayOfWeekStr = "목";
                } else {
                    dayOfWeekStr = "(Thu)";
                }
            }
            else if (dayOfWeek == 6)
            {
                if ((lang.equals("KOR")) && (cnt == 1)) {
                    dayOfWeekStr = "금";
                } else {
                    dayOfWeekStr = "(Fri)";
                }
            }
            else if (dayOfWeek == 7) {
                if ((lang.equals("KOR")) && (cnt == 1)) {
                    dayOfWeekStr = "토";
                } else {
                    dayOfWeekStr = "(Sat)";
                }
            }
            return dayOfWeekStr;
        }
        return "";
    }

    public static String getPostgresDeleteDateFormat(String dateTime)
    {
        StringBuffer sb = new StringBuffer();
        if ((dateTime == null) || (dateTime.equals(""))) {
            return "";
        }
        sb.append(dateTime.substring(0, 4));
        sb.append(dateTime.substring(5, 7));
        sb.append(dateTime.substring(8, 10));
        sb.append(dateTime.substring(11, 13));
        sb.append(dateTime.substring(14, 16));
        sb.append(dateTime.substring(17));
        return sb.toString();
    }

    public static String getFullDateTime()
    {
        String msgDate = "";

        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss");
        msgDate = simpleDate.format(new Date());
        return msgDate;
    }

    public static ArrayList getDateGap(String stDate, String edDate, String gubun)
    {
        Calendar sDate = getDateCalendarType(stDate);
        Calendar eDate = getDateCalendarType(edDate);

        Calendar monthStart = sDate;

        Calendar monthEnd = eDate;

        ArrayList<String> date = new ArrayList();
        if (gubun.equals("day")) {
            for (Calendar start = monthStart; monthEnd.compareTo(start) >= 0; start.add(5, 1)) {
                date.add(getDateStringType(monthStart));
            }
        } else {
            for (Calendar start = monthStart; monthEnd.compareTo(start) >= 0; start.add(2, 1)) {
                date.add(getMonthStringType(monthStart));
            }
        }
        return date;
    }

    public static String getTerm()
    {
        String plan_term = "";
        int currentDate = Integer.parseInt(getToday().substring(4, 8));
        if ((currentDate >= 301) && (currentDate < 901)) {
            plan_term = "1";
        } else {
            plan_term = "2";
        }
        return plan_term;
    }

    public static String getSchoolYear()
    {
        String school_year = "";

        String currentDate = getToday();
        int currentMonth = Integer.parseInt(currentDate.substring(4, 6));
        if (currentMonth >= 3) {
            school_year = currentDate.substring(0, 4);
        } else {
            school_year = getPrevOneYearDay(currentDate).substring(0, 4);
        }
        return school_year;
    }
}
