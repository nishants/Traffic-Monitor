package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Reading;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.geeksaint.traffix.interpret.InitialState.create;
import static com.geeksaint.traffix.maker.ReadingMaker.hoseAReading;
import static com.geeksaint.traffix.maker.ReadingMaker.hoseBReading;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.*;
import static org.junit.Assert.assertThat;

public class InitialStateTest {
  private InitialState initialState;
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setup(){
    this.initialState = create();
  }

  @Test
  public void shouldNotHaveOutput() {
    assertThat(initialState.getOutput(), is(nullValue()));
    assertThat(initialState.hasOutput(), is(false));
  }

  @Test
  public void shouldThrowExceptionIfReadingIsOfHoseB(){
    Reading reading = hoseBReading;

    expectedException.expect(UnexpectedReadingException.class);
    expectedException.expectMessage("Expected hose A reading, found hose B");

    initialState.input(reading);
  }

  @Test
  public void nextStateShouldBeFrontAxleStateForValidInput() {
    Reading reading = hoseAReading;
    FrontAxleOnHoseA expectedState = FrontAxleOnHoseA.with(reading);

    InterpreterState nextState = initialState.input(reading);

    assertThat(expectedState, is(nextState));
  }
}
