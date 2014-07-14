package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Recording;
import lombok.Getter;

//Represents the state of the interpreter after it has found the reading for the first tyre of a vehicle crossing the hose A
public class FrontTyreCrossedState implements InterpreterState{

  @Getter
  private Reading reading;

  protected FrontTyreCrossedState(Reading reading) {
    this.reading = reading;
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

  public static InterpreterState with(Reading reading) {
    return new FrontTyreCrossedState(reading);
  }
}
