package com.geeksaint.traffix.util;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static com.geeksaint.traffix.util.DateSupport.timeOfDayInMinutes;
import static com.geeksaint.traffix.util.DateSupport.timeOfDayInSeconds;
import static com.geeksaint.traffix.util.DateSupport.toDateStamp;
import static java.util.Calendar.JANUARY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DateSupportTest {
  @Test
  public void shouldReturnTimeOfDay(){
    Calendar calendar = Calendar.getInstance();
    calendar.set(2014, JANUARY, 3, 1, 1, 1);
    int timeInSec = timeOfDayInSeconds(calendar.getTime());
    assertThat(timeInSec, is(1*60*60 + 1 *60+ 1));
  }

  @Test
  public void shouldConvertToMinutes(){
    Calendar calendar = Calendar.getInstance();
    calendar.set(2014, JANUARY, 3, 1, 1, 1);
    int timeInMin = timeOfDayInMinutes(calendar.getTime());

    assertThat(timeInMin, is(61));
  }

  @Test
  public void shouldReturnDateString(){
    Calendar calendar = Calendar.getInstance();
    calendar.set(2014, JANUARY, 3, 1, 1, 1);
    Date date = calendar.getTime();
    assertThat(toDateStamp(date), is("030114"));
  }
}
