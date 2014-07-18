package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Reading;
import com.geeksaint.traffix.VehicleData;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.geeksaint.traffix.maker.ReadingMaker.hoseAReading;
import static com.geeksaint.traffix.maker.ReadingMaker.hoseBReading;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LaneAVehicleFoundStateTest {

  private Reading readingOfSecondAxle;
  private Reading readingOfFirstAxle;
  private LaneAVehicleFoundState laneAVehicleFoundState;
  @Rule
  public ExpectedException expectedException = ExpectedException.none();


  @Before
  public void setup() {
    readingOfFirstAxle = hoseAReading;
    readingOfSecondAxle = hoseAReading;
    laneAVehicleFoundState = LaneAVehicleFoundState.with(readingOfFirstAxle, readingOfSecondAxle);
  }

  @Test
  public void shouldHaveOutput() {
    VehicleData expected = VehicleData.record(asList(readingOfFirstAxle, readingOfSecondAxle));
    assertThat(laneAVehicleFoundState.hasOutput(), is(true));
    assertThat(laneAVehicleFoundState.getOutput(), is(expected));
  }

  @Test
  public void shouldThrowExceptionIfNextReadingIsOfHoseB(){
    Reading nextReading = hoseBReading;
    expectedException.expect(UnexpectedReadingException.class);
    expectedException.expectMessage("Expected hose A reading, found hose B");

    laneAVehicleFoundState.input(nextReading);
  }

  @Test
  public void nextStateMustBeInitialFrontAxleCrossedState() {
    Reading nextReading = hoseAReading;

    FrontAxleOnHoseA expectedState = FrontAxleOnHoseA.with(nextReading);

    InterpreterState nextState = laneAVehicleFoundState.input(nextReading);
    assertThat(expectedState, is(nextState));
  }
}
