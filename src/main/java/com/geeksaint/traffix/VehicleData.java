package com.geeksaint.traffix;

import com.geeksaint.traffix.interpret.Reading;

import java.util.List;

//Represents one vehicle data
public class VehicleData {

  public VehicleData(List<Reading> readings) {}

  public static VehicleData record(List<Reading> readings){
    return new VehicleData(readings);
  }
}
