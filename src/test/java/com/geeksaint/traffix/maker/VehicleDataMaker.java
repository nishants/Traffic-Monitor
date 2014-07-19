package com.geeksaint.traffix.maker;

import com.geeksaint.traffix.Lane;
import com.geeksaint.traffix.VehicleData;
import com.geeksaint.traffix.persist.TrafficReport;
import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;

import java.util.Date;

import static com.geeksaint.traffix.maker.TrafficReportMaker.Report;
import static com.geeksaint.traffix.maker.TrafficReportMaker.vehicleData;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static java.util.Arrays.asList;

public class VehicleDataMaker {
  public static final Property<VehicleData, Long> time = new Property<VehicleData, Long>();
  public static final Property<VehicleData, Float> speed = new Property<VehicleData, Float>();
  public static final Property<VehicleData, Lane> lane = new Property<VehicleData, Lane>();

  public static final Instantiator<VehicleData> VehicleData = new Instantiator<VehicleData>() {
    public VehicleData instantiate(PropertyLookup<VehicleData> lookup) {
      return new VehicleData(lookup.valueOf(speed, 16f), new Date(lookup.valueOf(time, 0l)), lookup.valueOf(lane, Lane.LANE_A) );
    }
  };

  public static VehicleData vehicleWith(long time, float speed, Lane lane) {
    return make(a(VehicleData,
        with(VehicleDataMaker.time, time),
        with(VehicleDataMaker.speed, speed),
        with(VehicleDataMaker.lane, lane)));
  }

  public static TrafficReport reportFor(VehicleData...vehicleDataList) {
    return make(a(Report, with(vehicleData, asList(vehicleDataList))));
  }
}



