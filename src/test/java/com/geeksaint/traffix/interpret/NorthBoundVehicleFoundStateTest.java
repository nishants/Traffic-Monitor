package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Recording;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.geeksaint.traffix.interpret.Reading.of;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {"com.geeksaint.traffix.Recording", "com.geeksaint.traffix.interpret.FrontAxleCrossedState"})
public class NorthBoundVehicleFoundStateTest {

  private Reading readingOfSecondAxle;
  private Reading readingOfFirstAxle;
  private NorthBoundVehicleFoundState northBoundVehicleFoundState;

  @Before
  public void setup() {
    readingOfFirstAxle = Reading.of("A1001");
    readingOfSecondAxle = Reading.of("A1162");

    northBoundVehicleFoundState = NorthBoundVehicleFoundState.with(readingOfFirstAxle, readingOfSecondAxle);
  }

  @Test
  public void shouldHaveOutput() {
    Recording expectedOutput = Recording.record(asList(readingOfFirstAxle, readingOfSecondAxle));

    mockStatic(Recording.class);
    when(Recording.record(asList(readingOfFirstAxle, readingOfSecondAxle))).thenReturn(expectedOutput);

    assertThat(northBoundVehicleFoundState.hasOutput(), is(true));
    assertThat(northBoundVehicleFoundState.getOutput(), is(expectedOutput));
  }

  @Test
  public void nextStateMustBeInitialFrontAxleCrossedState(){
    Reading nextReading = of("A123");

    FrontAxleCrossedState expectedState = new FrontAxleCrossedState(nextReading);

    mockStatic(FrontAxleCrossedState.class);
    when(FrontAxleCrossedState.with(nextReading)).thenReturn(expectedState);

    InterpreterState nextState = northBoundVehicleFoundState.input(nextReading);
    assertThat(expectedState, is(nextState));
  }
}
