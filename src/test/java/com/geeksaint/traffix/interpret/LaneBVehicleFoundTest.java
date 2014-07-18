package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Reading;
import com.geeksaint.traffix.VehicleData;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.geeksaint.traffix.maker.ReadingMaker.Reading;
import static com.geeksaint.traffix.maker.ReadingMaker.hoseBReading;
import static com.geeksaint.traffix.maker.ReadingMaker.lane;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LaneBVehicleFoundTest {
  private InterpreterState state;
  private com.geeksaint.traffix.Reading frontAxleHoseAReading;
  private Reading frontAxleHoseBReading;
  private Reading backAxleHoseAReading;
  private Reading backAxleHoseBReading;
  @Rule
  public ExpectedException expectedException = ExpectedException.none();


  @Before
  public void setup() {
    frontAxleHoseAReading = make(a(Reading, with(lane, LANE_A)));
    frontAxleHoseBReading = make(a(Reading, with(lane, LANE_B)));
    backAxleHoseAReading = make(a(Reading, with(lane, LANE_A)));
    backAxleHoseBReading = make(a(Reading, with(lane, LANE_B)));

    state = LaneBVehicleFound.withReadings(
        frontAxleHoseAReading,
        frontAxleHoseBReading,
        backAxleHoseAReading,
        backAxleHoseBReading);
  }

  @Test
  public void shouldThrowExceptionIfHoseBReadingFound(){
    expectedException.expect(UnexpectedReadingException.class);
    expectedException.expectMessage("Expected hose A reading, found hose B");
    state.input(hoseBReading);
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
