package com.geeksaint.traffix.interpret;

import org.junit.Test;

import static com.geeksaint.traffix.interpret.Reading.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReadingTest {

  @Test
  public void isNorthBoundIfStartsWithA() {
    Reading pointAReading = of("A12345");
    assertThat(pointAReading.isOfPointA(), is(true));

    Reading pointBReading = of("B12345");
    assertThat(pointBReading.isOfPointA(), is(false));
  }

  @Test
  public void shouldHaveAStringValue() {
    String value = "A12345";
    Reading reading = of(value);
    assertThat(reading.getValue(), is(value));
  }
}
