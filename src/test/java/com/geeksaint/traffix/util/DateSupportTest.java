package com.geeksaint.traffix.util;

import org.junit.Test;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DateSupportTest {
  @Test
  public void shouldReturnTimeOfDay(){
    Calendar calendar = Calendar.getInstance();
    calendar.set(2014, 1, 3, 1, 1, 1);
    int timeInSec = DateSupport.timeOfDayInSeconds(calendar.getTime());
    assertThat(timeInSec, is(1*60*60 + 1 *60+ 1));
  }
}
