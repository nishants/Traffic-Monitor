package com.geeksaint.traffix.interpret;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReadingTest {

  @Test
  public void shouldHaveAStringValue(){
    String value = "A12345";
    Reading reading = Reading.of(value);
    assertThat(reading.getValue(), is(value));

  }
}
