package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.VehicleData;

/**
 * Created by Nishant on 7/18/14.
 */
public class BackAxleOnHoseA implements InterpreterState{
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

  public static InterpreterState withReadings(Reading frontAxleHoseAReading, Reading frontAxleHoseBReading, Reading reading) {
    return new BackAxleOnHoseA();
  }
}
