package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Recording;

//Represents the state of the interpreter after it has found the readingOfFirstTyre for the first tyre of a vehicle crossing the hose A
public class FrontTyreCrossedState implements InterpreterState {

  private final Reading readingOfFirstTyre;

  protected FrontTyreCrossedState(Reading readingOfFirstTyre) {
    this.readingOfFirstTyre = readingOfFirstTyre;
  }

  @Override
  public InterpreterState input(Reading readingOfSecondTyre) {
    if (readingOfSecondTyre.isOfPointA()) {
      return NorthBoundVehicleFoundState.with(readingOfFirstTyre, readingOfSecondTyre);
    }
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

  public static FrontTyreCrossedState with(Reading reading) {
    return new FrontTyreCrossedState(reading);
  }
}
