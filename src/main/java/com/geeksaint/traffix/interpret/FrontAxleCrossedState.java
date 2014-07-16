package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.VehicleData;

//Represents the state of the interpreter after it has found the readingOfFirstAxle for the first axle of a vehicle crossing the hose A
public class FrontAxleCrossedState implements InterpreterState {

  private final Reading readingOfFirstAxle;

  protected FrontAxleCrossedState(Reading readingOfFirstAxle) {
    this.readingOfFirstAxle = readingOfFirstAxle;
  }

  @Override
  public InterpreterState input(Reading readingOfSecondAxle) {
    if (readingOfSecondAxle.isLaneA()) {
      return NorthBoundVehicleFoundState.with(readingOfFirstAxle, readingOfSecondAxle);
    }
    return null;
  }

  @Override
  public boolean hasOutput() {
    return false;
  }

  @Override
  public VehicleData getOutput() {
    return null;
  }

  public static FrontAxleCrossedState with(Reading reading) {
    return new FrontAxleCrossedState(reading);
  }
}
