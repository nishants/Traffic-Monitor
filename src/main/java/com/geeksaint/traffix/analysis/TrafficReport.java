package com.geeksaint.traffix.analysis;

import com.geeksaint.traffix.VehicleData;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class TrafficReport {
  private final List<VehicleData> laneAData = new ArrayList<VehicleData>();
  private final List<VehicleData> laneBData = new ArrayList<VehicleData>();

  public TrafficReport(List<VehicleData> vehicleData){
   setData(vehicleData);
  }

  private void setData(List<VehicleData> vehicleDataList) {
    for(VehicleData vehicleData : vehicleDataList){
      if(vehicleData.isLaneA()){
        laneAData.add(vehicleData);
      }else{
        laneBData.add(vehicleData);
      }
    }
  }

  public static TrafficReport of(List<VehicleData> vehicleDataList) {
    return new TrafficReport(vehicleDataList);
  }
}
