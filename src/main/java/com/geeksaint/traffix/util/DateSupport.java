package com.geeksaint.traffix.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;

//Abstraction for hiding the nuances of java date api
public class DateSupport {

  public static Date toDateOfYear(int day, int month, int year) {
    Calendar calendar = Calendar.getInstance();
    resetTimeComponent(calendar);
    calendar.set(YEAR, year);
    calendar.set(MONTH, month);
    calendar.set(DAY_OF_MONTH, day);
    return calendar.getTime();
  }


  public static Date increment(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(date.getTime());
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    return calendar.getTime();
  }


  public static Date addDaysTo(Date day, int days) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(day.getTime());
    calendar.add(DAY_OF_MONTH, days);
    return calendar.getTime();
  }

  public static int timeOfDayInSeconds(Date time) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(time);
    return calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 +
        calendar.get(Calendar.MINUTE) * 60 +
        calendar.get(Calendar.SECOND);
  }

  //Converts the time into minutes past since 00:00
  public static int timeOfDayInMinutes(Date time) {
    return timeOfDayInSeconds(time) / 60;
  }

  // Return date stamp of format ddmmyy eg. 030114 for 3rd Jan 2012
  public static String toDateStamp(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
    return dateFormat.format(date);
  }


  public static long addDate(Date date, long timeStampToMillis) {
    return new Date(date.getTime() + timeStampToMillis).getTime();
  }

  public static long timeStampToMillis(String timeStampInHHMM) {
    long minutes = toMinutes(timeStampInHHMM);
    return minutes * 60 * 1000;
  }

  //Converts time into minutes since 00:00
  public static int toMinutes(String timeStampInMMSS) {
    int minutes = minuteOf(timeStampInMMSS);
    minutes += (hourOf(timeStampInMMSS) * 60);
    return minutes;
  }

  public static int minuteOf(String timeStampInMMSS) {
    return parseInt(timeStampInMMSS.substring(2, 4));
  }

  public static int hourOf(String timeStampInMMSS) {
    return parseInt(timeStampInMMSS.substring(0, 2));
  }

  public static Date toDateOfYear(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    resetTimeComponent(calendar);
    return calendar.getTime();
  }

  private static void resetTimeComponent(Calendar calendar) {
    calendar.set(MILLISECOND, 0);
    calendar.set(SECOND, 0);
    calendar.set(MINUTE, 0);
    calendar.set(HOUR_OF_DAY, 0);
  }
}
