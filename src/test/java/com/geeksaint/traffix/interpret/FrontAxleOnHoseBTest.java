package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Reading;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
  private com.geeksaint.traffix.Reading frontAxleHoseAReading;
  private Reading frontAxleHoseBReading;
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setup() {
    frontAxleHoseAReading = make(a(Reading, with(lane, LANE_A)));
    frontAxleHoseBReading = make(a(Reading, with(lane, LANE_B)));
    state = FrontAxleOnHoseB.withReadings(frontAxleHoseAReading, frontAxleHoseBReading);
  }

  @Test
  public void shouldThrowExceptionIfNextReadingIsHoseB(){
    expectedException.expect(UnexpectedReadingException.class);
    expectedException.expectMessage("Expected hose A reading, found hose B");
    state.input(hoseBReading);
  }

  @Test
  public void shouldHaveNoOutput() {
    assertThat(state.hasOutput(), is(false));
    assertThat(state.getOutput(), is(nullValue()));
  }

  @Test
  public void nextStateMustBeBackAxleOnHoseA() {
    InterpreterState expected = BackAxleOnHoseA.withReadings(frontAxleHoseAReading, frontAxleHoseBReading, hoseAReading);

    InterpreterState nextState = state.input(hoseAReading);

    assertThat(nextState, is(expected));
  }
}
