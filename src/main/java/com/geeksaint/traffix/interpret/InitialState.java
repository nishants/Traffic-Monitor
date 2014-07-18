package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Reading;
import com.geeksaint.traffix.VehicleData;

import static com.geeksaint.traffix.interpret.UnexpectedReadingException.*;

public class InitialState implements InterpreterState {

  private InitialState(){}

  @Override
  public InterpreterState input(Reading reading) {
    checkForHoseA(reading);
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
