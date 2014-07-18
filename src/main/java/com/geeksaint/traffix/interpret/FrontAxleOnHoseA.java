package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Reading;
import com.geeksaint.traffix.VehicleData;
import lombok.EqualsAndHashCode;

//Represents the state of the interpreter after it has found the readingOfFirstAxle for the first axle of a vehicle crossing the hose A
@EqualsAndHashCode
public class FrontAxleOnHoseA implements InterpreterState {

  private final Reading readingOfFirstAxle;

  private FrontAxleOnHoseA(Reading readingOfFirstAxle) {
    this.readingOfFirstAxle = readingOfFirstAxle;
  }

  @Override
  public InterpreterState input(Reading readingOfSecondAxle) {
    if (readingOfSecondAxle.isHoseA()) {
      return LaneAVehicleFoundState.with(readingOfFirstAxle, readingOfSecondAxle);
    }
    return FrontAxleOnHoseB.withReadings(readingOfFirstAxle, readingOfSecondAxle);
  }

  @Override
  public boolean hasOutput() {
    return false;
  }

  @Override
  public VehicleData getOutput() {
    return null;
  }

  public static FrontAxleOnHoseA with(Reading reading) {
    return new FrontAxleOnHoseA(reading);
  }
}
