package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.VehicleData;

public class SouthBoundVehicleFound implements InterpreterState{
  @Override
  public InterpreterState input(Reading reading) {
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

  public static InterpreterState withReading(Reading frontAxleHoseAReading, Reading frontAxleHoseBReading, Reading backAxleHoseAReading, Reading reading) {
    return null;
  }
}
