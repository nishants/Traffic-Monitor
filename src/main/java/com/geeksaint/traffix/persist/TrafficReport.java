package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class TrafficReport {
  private final LaneReport laneAReport;
  private final LaneReport laneBReport;
  private List<VehicleData> vehicleDataList;

  public TrafficReport(List<VehicleData> vehicleDataList) {
    laneAReport = null;
    laneBReport = null;
    this.vehicleDataList = vehicleDataList;
  }

  public static TrafficReport prepare(List<VehicleData> vehicleDataList) {
    return new TrafficReport(vehicleDataList);
  }

  public TrafficReport merge(TrafficReport withReport){
    LaneReport mergedLaneAReport = getLaneAReport().merge(withReport.getLaneAReport());
    LaneReport mergedLaneBReport = getLaneBReport().merge(withReport.getLaneBReport());
    return prepare(mergedLaneAReport, mergedLaneBReport);
  }

  private TrafficReport prepare(LaneReport mergedLaneAReport, LaneReport mergedLaneBReport) {
    return null;
  }
}
