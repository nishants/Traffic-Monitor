package com.geeksaint.traffix.analysis;

import com.geeksaint.traffix.VehicleData;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
public class TrafficReport {
  private List<VehicleData> vehicleData;

  public TrafficReport(List<VehicleData> vehicleData){
    this.vehicleData = vehicleData;
  }

  public static TrafficReport of(List<VehicleData> vehicleDataList) {
    return new TrafficReport(vehicleDataList);
  }
}
