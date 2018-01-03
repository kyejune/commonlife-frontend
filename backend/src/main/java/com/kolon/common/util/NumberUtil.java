package com.kolon.common.util;

import java.text.DecimalFormat;

/**
 * Created by agcdDev on 2017-07-07.
 */
public class NumberUtil
{
    public static double round(double d, int p)
    {
        long tmp = (long) Math.pow(10.0D, p);
        double num = Math.round(d * tmp);
        num /= tmp;
        return num;
    }

    public static double ceil(double d, int p)
    {
        long tmp = (long)Math.pow(10.0D, p);
        double num = Math.ceil(d * tmp);
        num /= tmp;
        return num;
    }

    public static double ceil2(double d, int p)
    {
        long tmp = (long)Math.pow(10.0D, p);
        double num = Math.ceil(d / tmp);
        num *= tmp;
        return num;
    }

    public static double floor(double d, int p)
    {
        long tmp = (long)Math.pow(10.0D, p);
        double num = Math.floor(d * tmp);
        num /= tmp;
        return num;
    }

    public static String decimalFormat(double value, int decimalCnt)
    {
        String resultValue = "";
        String pattern = "";
        if (decimalCnt > 0)
        {
            for (int i = 0; i < decimalCnt; i++) {
                pattern = pattern + "#";
            }
            pattern = "." + pattern;
            DecimalFormat df = new DecimalFormat(pattern);
            resultValue = df.format(value);
        }
        else
        {
            resultValue = String.valueOf(value);
        }
        return resultValue;
    }

    public static int objToInt(Object o)
    {
        return Integer.valueOf(String.valueOf(o)).intValue();
    }

    public static Integer toNumber(Object o1, Object o2)
    {
        Number n1 = toNumber(o1);
        Number n2 = toNumber(o2);
        if ((n1 == null) || (n2 == null) || (n2.intValue() == 0)) {
            return null;
        }
        int value = n1.intValue() % n2.intValue();
        return Integer.valueOf(value);
    }

    public static Number toNumber(Object num)
    {
        if ((num instanceof Number)) {
            return (Number)num;
        }
        return null;
    }
}