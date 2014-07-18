package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Reading;
import com.geeksaint.traffix.VehicleData;
import lombok.EqualsAndHashCode;

import static com.geeksaint.traffix.interpret.UnexpectedReadingException.*;

//Front Axle of a lane B vehicle crosses hose B
@EqualsAndHashCode
public class FrontAxleOnHoseB implements InterpreterState{
  private final Reading frontAxleHoseAReading;
  private final Reading frontAxleHoseBReading;

  private FrontAxleOnHoseB(Reading frontAxleHoseAReading, Reading frontAxleHoseBReading) {
    this.frontAxleHoseAReading = frontAxleHoseAReading;
    this.frontAxleHoseBReading = frontAxleHoseBReading;
  }

  @Override
  public InterpreterState input(Reading reading) {
    checkForHoseA(reading);
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
    return new FrontAxleOnHoseB(frontAxleHoseAReading, frontAxleHoseBReading);
  }
}
