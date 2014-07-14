package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Recording;

//Represents a state in which interpreter has discovered readings for second tyre of a north bound vehicle.
public class NorthBoundVehicleFoundState implements InterpreterState{
  private final Reading readingOfFirstTyre;
  private final Reading readingOfSecondTyre;

  public NorthBoundVehicleFoundState(Reading readingOfFirstTyre, Reading readingOfSecondTyre) {
    this.readingOfFirstTyre = readingOfFirstTyre;
    this.readingOfSecondTyre = readingOfSecondTyre;
  }

  @Override
  public InterpreterState input(Reading reading) {
    return null;
  }

  @Override
  public boolean hasOutput() {
    return false;
  }

  @Override
  public Recording getOutput() {
    return null;
  }

  public static NorthBoundVehicleFoundState with(Reading readingOfFirstTyre, Reading readingOfSecondTyre) {
    return new NorthBoundVehicleFoundState(readingOfFirstTyre, readingOfSecondTyre);
  }
}
