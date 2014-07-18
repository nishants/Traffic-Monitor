package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Reading;
import org.junit.Before;
import org.junit.Test;

import static com.geeksaint.traffix.maker.ReadingMaker.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class FrontAxleOnHoseATest {
  private FrontAxleOnHoseA frontAxleOnHoseA;
  private com.geeksaint.traffix.Reading readingOfFirstAxle;

  @Before
  public void setup() {
    readingOfFirstAxle = hoseAReading;
    frontAxleOnHoseA = FrontAxleOnHoseA.with(readingOfFirstAxle);
  }

  @Test
  public void shouldNotBeOutputState() {
    assertThat(frontAxleOnHoseA.getOutput(), is(nullValue()));
    assertThat(frontAxleOnHoseA.hasOutput(), is(false));
  }

  @Test
  public void shouldTransitToLaneVehicleFoundStateForHoseAReading() {
    Reading readingOfSecondAxle = hoseAReading;
    LaneAVehicleFoundState expectedState = LaneAVehicleFoundState.with(readingOfFirstAxle, readingOfSecondAxle);

    InterpreterState nextState = frontAxleOnHoseA.input(readingOfSecondAxle);
    assertThat(expectedState, is(nextState));
  }

  @Test
  public void shouldTransitToLaneVehicleFoundStateForHoseBReading() {
    InterpreterState expectedState = FrontAxleOnHoseB.withReadings(readingOfFirstAxle, hoseBReading);

    InterpreterState nextState = frontAxleOnHoseA.input(hoseBReading);
    assertThat(expectedState, is(nextState));
  }
}
