package com.db.schoolapp.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFunction {

    public static String convert(String date, String s, String s1) throws ParseException {
        SimpleDateFormat sdf = null;
        SimpleDateFormat sdf2 = null;
        try {
            sdf = new SimpleDateFormat(s);
            sdf2 = new SimpleDateFormat(s1);
            System.out.println(sdf2.format(sdf.parse(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf2.format(sdf.parse(date));
    }

    public static String converttime(String toString, String s, String s1) throws ParseException {
        SimpleDateFormat time12Format = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat time24Format = new SimpleDateFormat("HH:mm");

        return time24Format.format(time12Format.parse(toString));
    }

    public static String converttime1(String toString, String s, String s1) throws ParseException {
        SimpleDateFormat time12Format = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat time24Format = new SimpleDateFormat("HH:mm:ss");

        return time24Format.format(time12Format.parse(toString));
    }
}
