package com.geeksaint.traffix.interpret;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.geeksaint.traffix.maker.ReadingMaker.Reading;
import static com.geeksaint.traffix.maker.ReadingMaker.lane;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

//Back axle of vehicle crosses hose A
@RunWith(PowerMockRunner.class)
@PrepareForTest(SouthBoundVehicleFound.class)
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
    mockStatic(SouthBoundVehicleFound.class);

    InterpreterState expected = mock(SouthBoundVehicleFound.class);
    when(SouthBoundVehicleFound.withReadings(
        frontAxleHoseAReading,
        frontAxleHoseBReading,
        backAxleHoseAReading,
        backAxleHoseBReading)).thenReturn(expected);

    InterpreterState nextState = state.input(backAxleHoseBReading);
    assertThat(nextState, is(instanceOf(SouthBoundVehicleFound.class)));
  }
}
