package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.VehicleData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.geeksaint.traffix.maker.ReadingMaker.pointAReading;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {"com.geeksaint.traffix.VehicleData", "com.geeksaint.traffix.interpret.FrontAxleOnHoseA"})
public class NorthBoundVehicleFoundStateTest {

  private Reading readingOfSecondAxle;
  private Reading readingOfFirstAxle;
  private NorthBoundVehicleFoundState northBoundVehicleFoundState;

  @Before
  public void setup() {
    readingOfFirstAxle = pointAReading;
    readingOfSecondAxle = pointAReading;
    northBoundVehicleFoundState = NorthBoundVehicleFoundState.with(readingOfFirstAxle, readingOfSecondAxle);
  }

  @Test
  public void shouldHaveOutput() {
    VehicleData expectedOutput = VehicleData.record(asList(readingOfFirstAxle, readingOfSecondAxle));

    mockStatic(VehicleData.class);
    when(VehicleData.record(asList(readingOfFirstAxle, readingOfSecondAxle))).thenReturn(expectedOutput);

    assertThat(northBoundVehicleFoundState.hasOutput(), is(true));
    assertThat(northBoundVehicleFoundState.getOutput(), is(expectedOutput));
  }

  @Test
  public void nextStateMustBeInitialFrontAxleCrossedState(){
    Reading nextReading = pointAReading;

    FrontAxleOnHoseA expectedState = new FrontAxleOnHoseA(nextReading);

    mockStatic(FrontAxleOnHoseA.class);
    when(FrontAxleOnHoseA.with(nextReading)).thenReturn(expectedState);

    InterpreterState nextState = northBoundVehicleFoundState.input(nextReading);
    assertThat(expectedState, is(nextState));
  }
}
