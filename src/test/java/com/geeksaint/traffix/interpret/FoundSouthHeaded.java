package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.VehicleData;

//Front Axle of a south bound vehicle crosses hose B
public class FoundSouthHeaded implements InterpreterState{
  private final Reading frontAxleHoseAReading;
  private final Reading frontAxleHoseBReading;

  protected FoundSouthHeaded(Reading frontAxleHoseAReading, Reading frontAxleHoseBReading) {
    this.frontAxleHoseAReading = frontAxleHoseAReading;
    this.frontAxleHoseBReading = frontAxleHoseBReading;
  }

  @Override
  public InterpreterState input(Reading reading) {
    return BackAxleOnHoseA.withReadings(frontAxleHoseAReading, frontAxleHoseBReading, reading);
  }

  @Override
  public boolean hasOutput() {
    return false;
  }

  @Override
  public VehicleData getOutput() {
    return null;
  }

  public static InterpreterState withReadings(Reading frontAxleHoseAReading, Reading frontAxleHoseBReading) {
    return new FoundSouthHeaded(frontAxleHoseAReading, frontAxleHoseBReading);
  }
}
