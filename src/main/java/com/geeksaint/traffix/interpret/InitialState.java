package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Recording;

public class InitialState implements InterpreterState {

  @Override
  public InterpreterState input(Reading reading) {
    return FrontTyreCrossedState.with(reading);
  }

  @Override
  public boolean hasOutput() {
    return false;
  }

  @Override
  public Recording getOutput() {
    return null;
  }

  public static InitialState create() {
    return new InitialState();
  }
}
