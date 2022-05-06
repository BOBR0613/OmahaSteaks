package com.omahasteaks.utils.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTimeHelper {
    public static Date getDateFromString(String date, String dateFormatString) {
	Date returnDate = null;
	DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
	try {
	    if (date.contains("/")) {
		returnDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(returnDate);
		String[] array = date.split("/");
		String operator = array[0].substring(0, 1);
		int number = Integer.parseInt(array[0].substring(1, array[0].length()));
		if (operator.equals("-")) {
		    number = -number;
		}
		switch (array[1].toUpperCase()) {
		case "DAY": {
		    calendar.add(Calendar.DATE, number);
		    break;
		}
		case "MONTH": {
		    calendar.add(Calendar.MONTH, number);
		    break;
		}
		case "YEAR": {
		    calendar.add(Calendar.YEAR, number);
		    break;
		}
		default:
		    break;
		}
		returnDate = calendar.getTime();
	    } else if (date.isEmpty()) {
		returnDate = new Date();
	    } else {
		returnDate = dateFormat.parse(date);
	    }
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	return returnDate;
    }

    public static String getDateAsString(Date date, String dateFormatString) {
	DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
	return dateFormat.format(date);
    }

    public static int getDayInt(Date date, String dayFormatString) {
	SimpleDateFormat dayFormat = new SimpleDateFormat(dayFormatString);
	return Integer.parseInt(dayFormat.format(date));
    }

    public static int getMonthInt(Date date, String monthFormatString) {
	SimpleDateFormat monthFormat = new SimpleDateFormat(monthFormatString);
	return Integer.parseInt(monthFormat.format(date));
    }

    public static int getYearInt(Date date, String yearFormatString) {
	SimpleDateFormat yearFormat = new SimpleDateFormat(yearFormatString);
	return Integer.parseInt(yearFormat.format(date));
    }

    public static String getDurationAsString(long miliseconds) {
	long millis = miliseconds;
	long hours = TimeUnit.MILLISECONDS.toHours(millis);
	millis -= TimeUnit.HOURS.toMillis(hours);
	long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
	millis -= TimeUnit.MINUTES.toMillis(minutes);
	long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
	millis -= TimeUnit.MILLISECONDS.toMillis(millis);
	return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, millis);
    }
}
