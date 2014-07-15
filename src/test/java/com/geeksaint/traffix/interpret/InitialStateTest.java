package com.geeksaint.traffix.interpret;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.geeksaint.traffix.interpret.InitialState.create;
import static com.geeksaint.traffix.maker.ReadingMaker.pointAReading;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.*;
import static org.junit.Assert.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FrontAxleCrossedState.class)
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
    PowerMockito.mockStatic(FrontAxleCrossedState.class);
    Reading reading = pointAReading;
    FrontAxleCrossedState expectedState = new FrontAxleCrossedState(reading);
    PowerMockito.when(FrontAxleCrossedState.with(reading)).thenReturn(expectedState);

    InterpreterState nextState = initialState.input(reading);

    assertThat(expectedState, is(nextState));
  }
}
