package com.geeksaint.traffix.maker;

import com.geeksaint.traffix.Lane;
import com.geeksaint.traffix.Reading;
import com.geeksaint.traffix.VehicleData;
import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;

import java.util.Date;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;

public class VehicleDataMaker {
  public static final Property<VehicleData, Long> time = new Property<VehicleData, Long>();
  public static final Property<VehicleData, Float> speed = new Property<VehicleData, Float>();

  public static final Instantiator<VehicleData> VehicleData = new Instantiator<VehicleData>() {
    public VehicleData instantiate(PropertyLookup<VehicleData> lookup) {
      return new VehicleData(lookup.valueOf(speed, 16f), new Date(lookup.valueOf(time, 0l)), Lane.LANE_A);
    }
  };
}


