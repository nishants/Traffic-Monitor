package com.geeksaint.traffix.interpret;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.geeksaint.traffix.maker.ReadingMaker.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest(NorthBoundVehicleFoundState.class)
public class FrontAxleOnHoseATest {
  private FrontAxleOnHoseA frontAxleOnHoseA;
  private Reading readingOfFirstAxle;

  @Before
  public void setup() {
    readingOfFirstAxle = pointAReading;
    frontAxleOnHoseA = FrontAxleOnHoseA.with(readingOfFirstAxle);
  }

  @Test
  public void shouldNotBeOutputState() {
    assertThat(frontAxleOnHoseA.getOutput(), is(nullValue()));
    assertThat(frontAxleOnHoseA.hasOutput(), is(false));
  }

  @Test
  public void shouldTransitToNorthBoundVehicleFoundState() {
    Reading readingOfSecondAxle = pointAReading;
    NorthBoundVehicleFoundState expectedState = new NorthBoundVehicleFoundState(readingOfFirstAxle, readingOfSecondAxle);

    PowerMockito.mockStatic(NorthBoundVehicleFoundState.class);
    PowerMockito.when(NorthBoundVehicleFoundState.with(readingOfFirstAxle, readingOfSecondAxle)).thenReturn(expectedState);

    InterpreterState nextState = frontAxleOnHoseA.input(readingOfSecondAxle);
    assertThat(expectedState, is(nextState));
  }
}
