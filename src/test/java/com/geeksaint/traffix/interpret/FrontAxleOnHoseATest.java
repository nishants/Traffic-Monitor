package com.geeksaint.traffix.interpret;

import org.junit.Before;
import org.junit.Test;

import static com.geeksaint.traffix.maker.ReadingMaker.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class FrontAxleOnHoseATest {
  private FrontAxleOnHoseA frontAxleOnHoseA;
  private Reading readingOfFirstAxle;

  @Before
  public void setup() {
    readingOfFirstAxle = pointAReading;
    frontAxleOnHoseA = FrontAxleOnHoseA.with(readingOfFirstAxle);
  }

  @Test
  public void shouldNotBeOutputState() {
    assertThat(frontAxleOnHoseA.getOutput(), is(nullValue()));
    assertThat(frontAxleOnHoseA.hasOutput(), is(false));
  }

  @Test
  public void shouldTransitToNorthBoundVehicleFoundState() {
    Reading readingOfSecondAxle = pointAReading;
    NorthBoundVehicleFoundState expectedState = NorthBoundVehicleFoundState.with(readingOfFirstAxle, readingOfSecondAxle);

    InterpreterState nextState = frontAxleOnHoseA.input(readingOfSecondAxle);
    assertThat(expectedState, is(nextState));
  }
}
