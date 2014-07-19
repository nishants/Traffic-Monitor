package com.geeksaint.traffix.persist;

import org.junit.Before;
import org.junit.Test;

public class DailyTrafficDataTest {
  private DailyTrafficData trafficData;

  @Before
  public void setUp() throws Exception {
    trafficData = new DailyTrafficData(1, 1, 2014);
  }

  @Test
  public void shouldReturnCorrectReport(){

  }
}
