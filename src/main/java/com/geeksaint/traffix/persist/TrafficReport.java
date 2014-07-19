package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.geeksaint.traffix.persist.LaneReport.*;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
@ToString
public class TrafficReport {
  private final LaneReport laneAReport;
  private final LaneReport laneBReport;

  public static TrafficReport prepare(List<VehicleData> vehicleDataList) {
    List<VehicleData> laneAVehicles = new ArrayList<VehicleData>();
    List<VehicleData> laneBVehicles = new ArrayList<VehicleData>();
    for(VehicleData vehicleData : vehicleDataList){
      if(vehicleData.isLaneA()){
        laneAVehicles.add(vehicleData);
      }else {
        laneBVehicles.add(vehicleData);
      }
    }

    return new TrafficReport(
        prepareFor(laneAVehicles, LANE_A),
        prepareFor(laneBVehicles, LANE_B));
  }

  public TrafficReport merge(TrafficReport withReport){
    LaneReport mergedLaneAReport = getLaneAReport().merge(withReport.getLaneAReport());
    LaneReport mergedLaneBReport = getLaneBReport().merge(withReport.getLaneBReport());
    return new TrafficReport(mergedLaneAReport, mergedLaneBReport);
  }

  public static TrafficReport emptyReport() {
    return new TrafficReport(
        LaneReport.emptyReport(LANE_A),
        LaneReport.emptyReport(LANE_B));
  }
}
