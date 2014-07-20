package com.geeksaint.traffix.maker;

import com.geeksaint.traffix.DurationReport;
import com.geeksaint.traffix.IntervalReport;
import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;

import java.util.List;

import static com.geeksaint.traffix.maker.DurationReportMaker.*;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static java.util.Arrays.asList;

public class IntervalReportMaker {
  public static final Property<IntervalReport, List<DurationReport>>
      durationReports = new Property<IntervalReport, List<DurationReport>>();

  public static final Instantiator<IntervalReport> IntervalReport =
      new Instantiator<IntervalReport>() {
        public IntervalReport instantiate(PropertyLookup<IntervalReport> lookup) {
          return com.geeksaint.traffix.IntervalReport.prepareFrom(
              lookup.valueOf(durationReports, asList(make(a(DurationReport))))
          );
        }
      };
}
