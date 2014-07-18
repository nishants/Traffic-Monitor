package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Reading;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.geeksaint.traffix.maker.ReadingMaker.Reading;
import static com.geeksaint.traffix.maker.ReadingMaker.hoseAReading;
import static com.geeksaint.traffix.maker.ReadingMaker.hoseBReading;
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
  private com.geeksaint.traffix.Reading frontAxleHoseAReading;
  private Reading frontAxleHoseBReading;
  private Reading backAxleHoseAReading;
  @Rule
  public ExpectedException expectedException = ExpectedException.none();


  @Before
  public void setup(){
    frontAxleHoseAReading = make(a(Reading, with(lane, LANE_A)));
    frontAxleHoseBReading = make(a(Reading, with(lane, LANE_B)));
    backAxleHoseAReading = make(a(Reading, with(lane, LANE_A)));

    state = BackAxleOnHoseA.withReadings(frontAxleHoseAReading, frontAxleHoseBReading, backAxleHoseAReading);
  }

  @Test
  public void shouldThrowExceptionIfNextReadingIsHoseA(){
    expectedException.expect(UnexpectedReadingException.class);
    expectedException.expectMessage("Expected hose B reading, found hose A");
    state.input(hoseAReading);
  }

  @Test
  public void shouldNotHaveOutput(){
    assertThat(state.hasOutput(), is(false));
    assertThat(state.getOutput(), is(nullValue()));
  }

  @Test
  public void nextStateMustBeSouthBoundVehicleRecorded(){
    InterpreterState expected = LaneBVehicleFound.withReadings(
        frontAxleHoseAReading,
        frontAxleHoseBReading,
        backAxleHoseAReading,
        hoseBReading);

    InterpreterState nextState = state.input(hoseBReading);
    assertThat(nextState, is(expected));
  }
}
