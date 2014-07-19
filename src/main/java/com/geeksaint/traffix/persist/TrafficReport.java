package com.geeksaint.traffix.persist;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class TrafficReport {
  private final LaneReport laneAReport;
  private final LaneReport laneBReport;

  public static TrafficReport prepare(LaneReport laneAReport, LaneReport laneBReport) {
    return null;
  }

  public TrafficReport merge(TrafficReport withReport){
    LaneReport mergedLaneAReport = getLaneAReport().merge(withReport.getLaneAReport());
    LaneReport mergedLaneBReport = getLaneBReport().merge(withReport.getLaneBReport());
    return prepare(mergedLaneAReport, mergedLaneBReport);
  }
}
