package com.geeksaint.traffix.interpret;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.geeksaint.traffix.interpret.Reading.of;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest(NorthBoundVehicleFoundState.class)
public class FrontTyreCrossedStateTest {
  private FrontTyreCrossedState frontTyreCrossedState;
  private Reading readingOfFirstTyre;

  @Before
  public void setup() {
    readingOfFirstTyre = of("A1001");
    frontTyreCrossedState = FrontTyreCrossedState.with(readingOfFirstTyre);
  }

  @Test
  public void shouldNotBeOutputState() {
    assertThat(frontTyreCrossedState.getOutput(), is(nullValue()));
    assertThat(frontTyreCrossedState.hasOutput(), is(false));
  }

  @Test
  public void shouldTransitToNorthBoundVehicleFoundState() {
    Reading readingOfSecondTyre = of("A123");
    NorthBoundVehicleFoundState expectedState = new NorthBoundVehicleFoundState(readingOfFirstTyre, readingOfSecondTyre);

    PowerMockito.mockStatic(NorthBoundVehicleFoundState.class);
    PowerMockito.when(NorthBoundVehicleFoundState.with(readingOfFirstTyre, readingOfSecondTyre)).thenReturn(expectedState);

    InterpreterState nextState = frontTyreCrossedState.input(readingOfSecondTyre);
    assertThat(expectedState, is(nextState));
  }
}
