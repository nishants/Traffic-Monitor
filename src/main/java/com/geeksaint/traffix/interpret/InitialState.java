package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.VehicleData;

public class InitialState implements InterpreterState {

  @Override
  public InterpreterState input(Reading reading) {
    return FrontAxleOnHoseA.with(reading);
  }

  @Override
  public boolean hasOutput() {
    return false;
  }

  @Override
  public VehicleData getOutput() {
    return null;
  }

  public static InitialState create() {
    return new InitialState();
  }
}
