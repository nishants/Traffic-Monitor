package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.VehicleData;
import lombok.EqualsAndHashCode;

import static java.util.Arrays.asList;

//Represents a state in which interpreter has discovered readings for second axle of a north bound vehicle.
@EqualsAndHashCode
public class NorthBoundVehicleFoundState implements InterpreterState {
  private final Reading readingOfFirstAxle;
  private final Reading readingOfSecondAxle;

  public NorthBoundVehicleFoundState(Reading readingOfFirstAxle, Reading readingOfSecondAxle) {
    this.readingOfFirstAxle = readingOfFirstAxle;
    this.readingOfSecondAxle = readingOfSecondAxle;
  }

  @Override
  public InterpreterState input(Reading reading) {
    return FrontAxleOnHoseA.with(reading);
  }

  @Override
  public boolean hasOutput() {
    return true;
  }

  @Override
  public VehicleData getOutput() {
    return VehicleData.record(asList(readingOfFirstAxle, readingOfSecondAxle));
  }

  public static NorthBoundVehicleFoundState with(Reading readingOfFirstAxle, Reading readingOfSecondAxle) {
    return new NorthBoundVehicleFoundState(readingOfFirstAxle, readingOfSecondAxle);
  }
}
