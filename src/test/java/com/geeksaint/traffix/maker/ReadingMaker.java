package com.geeksaint.traffix.maker;

import com.geeksaint.traffix.Lane;
import com.geeksaint.traffix.Reading;
import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;

import java.util.Date;

import static com.geeksaint.traffix.Lane.*;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;

public class ReadingMaker {
  public static final Property<Reading, Date> time = new Property<Reading, Date>();
  public static final Property<Reading, Lane> lane = new Property<Reading, Lane>();

  public static final Instantiator<Reading> Reading = new Instantiator<Reading>() {
    public Reading instantiate(PropertyLookup<Reading> lookup) {
      Date recordedAt = lookup.valueOf(time, new Date(0l));
      Lane goingNorth = lookup.valueOf(lane, LANE_A);
      return com.geeksaint.traffix.Reading.of(recordedAt, goingNorth);
    }
  };

  public static Reading makeReading(Date observedOn, long timeInMillis, Lane lane){
    return make(a(Reading, with(ReadingMaker.lane, lane), with(time, addToDate(observedOn, timeInMillis))));
  }

  private static Date addToDate(Date observedOn, long timeInMillis) {
    return new Date(observedOn.getTime() + timeInMillis);
  }

  public static Reading hoseAReading = make(a(Reading));
  public static Reading hoseBReading = make(a(Reading, with(lane, LANE_B)));
}


