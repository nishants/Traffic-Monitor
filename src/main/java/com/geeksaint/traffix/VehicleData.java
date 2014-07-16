package com.geeksaint.traffix;

import com.geeksaint.traffix.interpret.Reading;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

//Represents one vehicle data
@EqualsAndHashCode
@ToString
public class VehicleData {

  private List<Reading> readings;

  public VehicleData(List<Reading> readings) {
    this.readings = readings;
  }

  public static VehicleData record(List<Reading> readings){
    return new VehicleData(readings);
  }
}
