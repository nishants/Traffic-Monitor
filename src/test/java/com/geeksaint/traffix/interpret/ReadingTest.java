package com.geeksaint.traffix.interpret;

import org.junit.Test;

import java.util.Date;

import static com.geeksaint.traffix.interpret.Reading.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReadingTest {

  @Test
  public void shouldMakeAReading() {
    Reading pointAReading = of(new Date(0l), true);
    assertThat(pointAReading.isOfPointA(), is(true));

    Reading pointBReading = of(new Date(0l), false);
    assertThat(pointBReading.isOfPointA(), is(false));
  }
}
