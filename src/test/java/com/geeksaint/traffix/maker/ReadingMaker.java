package com.geeksaint.traffix.maker;

import com.geeksaint.traffix.interpret.Reading;
import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;

import java.util.Date;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;

public class ReadingMaker {
  public static final Property<Reading, Date> time = new Property<Reading, Date>();
  public static final Property<Reading, Boolean> ofPointA = new Property<Reading, Boolean>();

  public static final Instantiator<Reading> Reading = new Instantiator<Reading>() {
    public Reading instantiate(PropertyLookup<Reading> lookup) {
      Date recordedAt = lookup.valueOf(time, new Date(0l));
      Boolean goingNorth = lookup.valueOf(ofPointA, true);
      return com.geeksaint.traffix.interpret.Reading.of(recordedAt, goingNorth);
    }
  };

  public static Reading pointAReading = make(a(Reading));
}


