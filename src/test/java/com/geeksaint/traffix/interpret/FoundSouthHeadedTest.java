package com.geeksaint.traffix.interpret;

import org.junit.Before;
import org.junit.Test;

import static com.geeksaint.traffix.Lane.*;
import static com.geeksaint.traffix.maker.ReadingMaker.*;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class FoundSouthHeadedTest {

  private InterpreterState state;
  @Before
  public void setup(){
    Reading frontAxleHoseAReading = make(a(Reading, with(lane, LANE_A)));
    Reading frontAxleHoseBReading = make(a(Reading, with(lane, LANE_B)));
    state = FoundSouthHeaded.withReadings(frontAxleHoseAReading, frontAxleHoseBReading);
  }

  @Test
  public void shouldHaveNoOutput(){
    assertThat(state.hasOutput(), is(false));
    assertThat(state.getOutput(), is(nullValue()));
  }

  @Test
  public void nextStateMustBeBackAxleOnHoseA(){
    Reading backAxleHoseBReading = make(a(Reading, with(lane, LANE_B)));
    assertThat(state.input(backAxleHoseBReading), is(instanceOf(BackAxleOnHoseA.class)));
  }
}
