package com.oromil.a65appstest.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oromil on 24.12.2017.
 */

public class DateUtils {

    private static final DateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat inputFormat2 = new SimpleDateFormat("dd-MM-yyyy");

    public static String formatDate(String dateStr) {
        if (dateStr == null)
            dateStr = "-";
        try {
            if (dateStr.equals(inputFormat.format(inputFormat.parse(dateStr))))
                dateStr = outputFormat.format(inputFormat.parse(dateStr));
            if (dateStr.equals(inputFormat2.format(inputFormat2.parse(dateStr))))
                dateStr = outputFormat.format(inputFormat2.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static String calulateAge(String birthday) {
        try {
            Date today = new Date();
            today.setTime(System.currentTimeMillis());
            Date birthdayDate = outputFormat.parse(birthday);
            int age = today.getYear() - birthdayDate.getYear();
            if (today.getMonth() < birthdayDate.getMonth() |
                    (today.getMonth() == birthdayDate.getMonth()
                            && today.getDay() < birthdayDate.getDay()))
                age--;
            return String.valueOf(age);
        } catch (ParseException e) {
            e.printStackTrace();
            return "-";
        }
    }
}
