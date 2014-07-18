package com.geeksaint.traffix.interpret;

import org.junit.Test;

import java.util.Date;

import static com.geeksaint.traffix.Lane.*;
import static com.geeksaint.traffix.interpret.Reading.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReadingTest {

  @Test
  public void shouldMakeAReading() {
    Reading pointAReading = of(new Date(0l), LANE_A);
    assertThat(pointAReading.isHoseA(), is(true));

    Reading pointBReading = of(new Date(0l), LANE_B);
    assertThat(pointBReading.isHoseA(), is(false));
  }
}
