package com.geeksaint.traffix.maker;

import com.geeksaint.traffix.VehicleData;
import com.geeksaint.traffix.persist.TrafficReport;
import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;

import java.util.List;

import static com.geeksaint.traffix.maker.VehicleDataMaker.*;
import static com.geeksaint.traffix.persist.TrafficReport.prepare;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static java.util.Arrays.asList;

public class TrafficReportMaker {
  public static final Property<TrafficReport, List<VehicleData>> vehicleData = new Property<TrafficReport, List<VehicleData>>();

  public static final Instantiator<TrafficReport> Report = new Instantiator<TrafficReport>() {
    public TrafficReport instantiate(PropertyLookup<TrafficReport> lookup) {
      return prepare(lookup.valueOf(vehicleData, asList(make(a(VehicleData)))));
    }
  };
}
