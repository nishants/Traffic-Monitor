package com.geeksaint.traffix.interpret;

import org.junit.Before;
import org.junit.Test;

import static com.geeksaint.traffix.interpret.InitialState.create;
import static com.geeksaint.traffix.maker.ReadingMaker.pointAReading;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.*;
import static org.junit.Assert.assertThat;

public class InitialStateTest {
  private InitialState initialState;

  @Before
  public void setup(){
    this.initialState = create();
  }

  @Test
  public void shouldNotBeOutputState() {
    assertThat(initialState.getOutput(), is(nullValue()));
    assertThat(initialState.hasOutput(), is(false));
  }

  @Test
  public void nextStateShouldBeFrontAxleStateForValidInput() {
    Reading reading = pointAReading;
    FrontAxleOnHoseA expectedState = FrontAxleOnHoseA.with(reading);

    InterpreterState nextState = initialState.input(reading);

    assertThat(expectedState, is(nextState));
  }
}
