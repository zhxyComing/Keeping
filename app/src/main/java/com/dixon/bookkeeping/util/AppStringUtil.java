package com.dixon.bookkeeping.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AppStringUtil {

    //将万元数字转为xxW的显示形式
    public static String getTenThousandNum(long num) {
        String originAmount = String.valueOf(num);
        if (originAmount.length() > 4) {
            StringBuilder res = new StringBuilder();
            int index = originAmount.length() - 4;
            String decimalLastString = getDecimalLastString(originAmount);
            res.append(originAmount.substring(0, index));
            if (!TextUtils.isEmpty(decimalLastString)) {
                res.append(".").append(decimalLastString);
            }
            res.append("W");
            return res.toString();
        } else {
            return String.format(Locale.CHINA, "%d", num);
        }
    }

    //小数点后数字
    private static String getDecimalLastString(String originAmount) {
        StringBuilder temp = new StringBuilder();
        boolean hasNotZero = false;
        for (int i = originAmount.length() - 1; i > originAmount.length() - 5; i--) {
            if (hasNotZero) {
                temp.append(originAmount.charAt(i));
                continue;
            }
            if (originAmount.charAt(i) != '0') {
                temp.append(originAmount.charAt(i));
                hasNotZero = true;
            }
        }
        return strReverseWithXor(temp.toString());
    }

    //字符串反转
    public static String strReverseWithXor(String string) {
        if (string == null || string.length() == 0) return string;
        char[] array = string.toCharArray();
        int length = string.length() - 1;
        for (int i = 0; i < length; i++, length--) {
            array[i] ^= array[length];
            array[length] ^= array[i];
            array[i] ^= array[length];
        }
        return new String(array);
    }

    public static String getDateString(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
        return timeFormat.format(date);
    }

    public static String getTodayString() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
        return timeFormat.format(new Date());
    }

    public static boolean isSameDay(String day1, String day2) {
        return day1 != null && day1.equals(day2);
    }


    /**
     * 获得指定日期的前一天
     *
     * @param currentTime
     * @return
     * @throws Exception
     */
    public static String getDayBefore(String currentTime) {
        if (TextUtils.isEmpty(currentTime)) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
        try {
            Date parse = format.parse(currentTime);
            if (parse != null) {
                Calendar c = Calendar.getInstance();
                c.setTime(parse);
                c.add(Calendar.DATE, -1);
                return format.format(c.getTime().getTime());
            }
            return "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获得指定日期的后一天
     *
     * @param currentTime
     * @return
     * @throws Exception
     */
    public static String getDayAfter(String currentTime) {
        if (TextUtils.isEmpty(currentTime)) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
        try {
            Date parse = format.parse(currentTime);
            if (parse != null) {
                Calendar c = Calendar.getInstance();
                c.setTime(parse);
                c.add(Calendar.DATE, +1);
                return format.format(c.getTime().getTime());
            }
            return "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long getTime(String currentTime) {
        if (TextUtils.isEmpty(currentTime)) {
            return 0;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
        try {
            Date parse = format.parse(currentTime);
            if (parse != null) {
                return parse.getTime();
            }
            return 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
