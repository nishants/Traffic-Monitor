package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.VehicleData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.geeksaint.traffix.maker.ReadingMaker.pointAReading;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NorthBoundVehicleFoundStateTest {

  private Reading readingOfSecondAxle;
  private Reading readingOfFirstAxle;
  private NorthBoundVehicleFoundState northBoundVehicleFoundState;

  @Before
  public void setup() {
    readingOfFirstAxle = pointAReading;
    readingOfSecondAxle = pointAReading;
    northBoundVehicleFoundState = NorthBoundVehicleFoundState.with(readingOfFirstAxle, readingOfSecondAxle);
  }

  @Test
  public void shouldHaveOutput() {
    VehicleData expected = VehicleData.record(asList(readingOfFirstAxle, readingOfSecondAxle));
    assertThat(northBoundVehicleFoundState.hasOutput(), is(true));
    assertThat(northBoundVehicleFoundState.getOutput(), is(expected));
  }

  @Test
  public void nextStateMustBeInitialFrontAxleCrossedState() {
    Reading nextReading = pointAReading;

    FrontAxleOnHoseA expectedState = FrontAxleOnHoseA.with(nextReading);

    InterpreterState nextState = northBoundVehicleFoundState.input(nextReading);
    assertThat(expectedState, is(nextState));
  }
}
