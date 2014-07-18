package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Reading;
import com.geeksaint.traffix.VehicleData;
import lombok.EqualsAndHashCode;

import static com.geeksaint.traffix.interpret.UnexpectedReadingException.*;

@EqualsAndHashCode
//Back axle crosses the hose A
public class BackAxleOnHoseA implements InterpreterState{
  private Reading frontAxleHoseAReading;
  private Reading frontAxleHoseBReading;
  private Reading backAxleHoseAReading;

  private BackAxleOnHoseA(Reading frontAxleHoseAReading, Reading frontAxleHoseBReading, Reading backAxleHoseAReading) {

    this.frontAxleHoseAReading = frontAxleHoseAReading;
    this.frontAxleHoseBReading = frontAxleHoseBReading;
    this.backAxleHoseAReading = backAxleHoseAReading;
  }

  @Override
  public InterpreterState input(Reading reading) {
    checkForHoseB(reading);
    return LaneBVehicleFound.withReadings(frontAxleHoseAReading, frontAxleHoseBReading, backAxleHoseAReading, reading);
  }

  @Override
  public boolean hasOutput() {
    return false;
  }

  @Override
  public VehicleData getOutput() {
    return null;
  }

  public static InterpreterState withReadings(Reading frontAxleHoseAReading, Reading frontAxleHoseBReading, Reading backAxleHoseAReading) {
    return new BackAxleOnHoseA(frontAxleHoseAReading, frontAxleHoseBReading, backAxleHoseAReading);
  }
}
