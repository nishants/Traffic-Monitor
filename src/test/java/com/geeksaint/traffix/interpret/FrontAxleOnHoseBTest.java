package com.geeksaint.traffix.interpret;

import org.junit.Before;
import org.junit.Test;

import static com.geeksaint.traffix.Lane.*;
import static com.geeksaint.traffix.maker.ReadingMaker.*;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class FrontAxleOnHoseBTest {

  private InterpreterState state;
  private Reading frontAxleHoseAReading;
  private Reading frontAxleHoseBReading;

  @Before
  public void setup() {
    frontAxleHoseAReading = make(a(Reading, with(lane, LANE_A)));
    frontAxleHoseBReading = make(a(Reading, with(lane, LANE_B)));
    state = FrontAxleOnHoseB.withReadings(frontAxleHoseAReading, frontAxleHoseBReading);
  }

  @Test
  public void shouldHaveNoOutput() {
    assertThat(state.hasOutput(), is(false));
    assertThat(state.getOutput(), is(nullValue()));
  }

  @Test
  public void nextStateMustBeBackAxleOnHoseA() {
    Reading backAxleHoseBReading = make(a(Reading, with(lane, LANE_B)));
    InterpreterState expected = BackAxleOnHoseA.withReadings(frontAxleHoseAReading, frontAxleHoseBReading, backAxleHoseBReading);

    InterpreterState nextState = state.input(backAxleHoseBReading);

    assertThat(nextState, is(expected));
  }
}
