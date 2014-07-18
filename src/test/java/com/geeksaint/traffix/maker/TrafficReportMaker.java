package com.geeksaint.traffix.maker;

import com.geeksaint.traffix.VehicleData;
import com.geeksaint.traffix.analysis.TrafficReport;
import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;

import java.util.List;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static java.util.Arrays.asList;

public class TrafficReportMaker {
  public static final Property<TrafficReport, List<VehicleData>> vehicleData = new Property<TrafficReport, List<VehicleData>>();

  public static final Instantiator<TrafficReport> Report = new Instantiator<TrafficReport>() {
    public TrafficReport instantiate(PropertyLookup<TrafficReport> lookup) {
      VehicleData aVehicleData = make(a(VehicleDataMaker.VehicleData));
      return TrafficReport.of(lookup.valueOf(vehicleData, asList(aVehicleData)));
    }
  };
}
