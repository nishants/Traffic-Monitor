package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.VehicleData;
import org.junit.Before;
import org.junit.Test;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.geeksaint.traffix.maker.ReadingMaker.Reading;
import static com.geeksaint.traffix.maker.ReadingMaker.lane;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SouthBoundVehicleFoundTest {
  private InterpreterState state;
  private Reading frontAxleHoseAReading;
  private Reading frontAxleHoseBReading;
  private Reading backAxleHoseAReading;
  private Reading backAxleHoseBReading;

  @Before
  public void setup() {
    frontAxleHoseAReading = make(a(Reading, with(lane, LANE_A)));
    frontAxleHoseBReading = make(a(Reading, with(lane, LANE_B)));
    backAxleHoseAReading = make(a(Reading, with(lane, LANE_A)));
    backAxleHoseBReading = make(a(Reading, with(lane, LANE_B)));

    state = SouthBoundVehicleFound.withReadings(
        frontAxleHoseAReading,
        frontAxleHoseBReading,
        backAxleHoseAReading,
        backAxleHoseBReading);
  }

  @Test
  public void nextStateShouldBeFrontAxleOnHoseA(){
    Reading nextReading = make(a(Reading, with(lane, LANE_A)));

    InterpreterState expected = FrontAxleOnHoseA.with(nextReading);
    assertThat(state.input(nextReading), is(expected));
  }

  @Test
  public void shouldHaveOutput(){
    VehicleData expected = VehicleData.record(asList(
        frontAxleHoseAReading,
        frontAxleHoseBReading,
        backAxleHoseAReading,
        backAxleHoseBReading
    ));
    assertThat(state.hasOutput(), is(true));
    assertThat(state.getOutput(), is(expected));
  }
}
