package com.geeksaint.traffix.maker;

import com.geeksaint.traffix.Lane;
import com.geeksaint.traffix.VehicleData;
import com.geeksaint.traffix.persist.LaneReport;
import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;

import java.util.List;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.maker.VehicleDataMaker.*;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static java.util.Arrays.asList;

public class LaneReportMaker {
  public static final Property<LaneReport, List<VehicleData>> vehicleData = new Property<LaneReport, List<VehicleData>>();
  public static final Property<LaneReport, Lane> lane = new Property<LaneReport, Lane>();

  public static final Instantiator<LaneReport> LaneReport = new Instantiator<LaneReport>() {
    public LaneReport instantiate(PropertyLookup<LaneReport> lookup) {
      List<VehicleData> vehicleDataList = lookup.valueOf(vehicleData, asList(make(a(VehicleData))));
      return com.geeksaint.traffix.persist.LaneReport.prepareFor(vehicleDataList, lookup.valueOf(lane, LANE_A));
    }
  };

  public static LaneReport laneReportWith(List<VehicleData> vehicleDataList, Lane laneForReport) {
    return make(a(LaneReport,
        with(vehicleData, vehicleDataList),
        with(lane, laneForReport)));
  }
}
