package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Reading;
import com.geeksaint.traffix.VehicleData;
import lombok.EqualsAndHashCode;

import static com.geeksaint.traffix.interpret.UnexpectedReadingException.*;
import static java.util.Arrays.asList;

//Back axle of a vehicle crossed the hose B
@EqualsAndHashCode
public class LaneBVehicleFound implements InterpreterState{
  private final Reading frontAxleHoseAReading;
  private final Reading frontAxleHoseBReading;
  private final Reading backAxleHoseAReading;
  private final Reading backAxleHoseBReading;

  private LaneBVehicleFound(Reading frontAxleHoseAReading,
                            Reading frontAxleHoseBReading,
                            Reading backAxleHoseAReading,
                            Reading backAxleHoseBReading) {
    this.frontAxleHoseAReading = frontAxleHoseAReading;
    this.frontAxleHoseBReading = frontAxleHoseBReading;
    this.backAxleHoseAReading = backAxleHoseAReading;
    this.backAxleHoseBReading = backAxleHoseBReading;
  }

  @Override
  public InterpreterState input(Reading reading) {
    checkForHoseA(reading);
    return FrontAxleOnHoseA.with(reading);
  }

  @Override
  public boolean hasOutput() {
    return true;
  }

  @Override
  public VehicleData getOutput() {
    return VehicleData.record(asList(
        frontAxleHoseAReading,
        frontAxleHoseBReading,
        backAxleHoseAReading,
        backAxleHoseBReading
    ));
  }

  public static InterpreterState withReadings(
      Reading frontAxleHoseAReading,
      Reading frontAxleHoseBReading,
      Reading backAxleHoseAReading,
      Reading backAxleHoseBReading) {

    return new LaneBVehicleFound(
        frontAxleHoseAReading,
        frontAxleHoseBReading,
        backAxleHoseAReading,
        backAxleHoseBReading);
  }
}
