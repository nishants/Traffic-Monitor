package com.geeksaint.traffix.interpret;

import org.junit.Before;
import org.junit.Test;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.geeksaint.traffix.maker.ReadingMaker.Reading;
import static com.geeksaint.traffix.maker.ReadingMaker.lane;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

//Back axle of vehicle crosses hose A
public class BackAxleOnHoseATest {

  private InterpreterState state;
  private Reading frontAxleHoseAReading;
  private Reading frontAxleHoseBReading;
  private Reading backAxleHoseAReading;

  @Before
  public void setup(){
    frontAxleHoseAReading = make(a(Reading, with(lane, LANE_A)));
    frontAxleHoseBReading = make(a(Reading, with(lane, LANE_B)));
    backAxleHoseAReading = make(a(Reading, with(lane, LANE_A)));

    state = BackAxleOnHoseA.withReadings(frontAxleHoseAReading, frontAxleHoseBReading, backAxleHoseAReading);
  }

  @Test
  public void shouldNotHaveOutput(){
    assertThat(state.hasOutput(), is(false));
    assertThat(state.getOutput(), is(nullValue()));
  }

  @Test
  public void nextStateMustBeSouthBoundVehicleRecorded(){
    Reading backAxleHoseBReading = make(a(Reading, with(lane, LANE_B)));

    InterpreterState expected = SouthBoundVehicleFound.withReadings(
        frontAxleHoseAReading,
        frontAxleHoseBReading,
        backAxleHoseAReading,
        backAxleHoseBReading);

    InterpreterState nextState = state.input(backAxleHoseBReading);
    assertThat(nextState, is(expected));
  }
}
