package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.Lane;
import com.geeksaint.traffix.VehicleData;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class LaneReport {
  final Lane lane;
  final long vehicleCount;
  final long sumOfSpeeds;

  //Sum of distance between vehicles
  final long sumOfDistances;

  public LaneReport prepareFor(List<VehicleData> vehicleDataList, Lane lane){
    return null;
  }

  public LaneReport merge(LaneReport withReport) {
    return null;
  }
}
