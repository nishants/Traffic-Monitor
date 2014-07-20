package com.geeksaint.traffix.maker;

import com.geeksaint.traffix.DurationReport;
import com.geeksaint.traffix.persist.TrafficReport;
import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;

import java.util.Date;
import java.util.List;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static java.util.Arrays.asList;

public class DurationReportMaker {
  public static final Property<DurationReport, Date> sessionStartTime = new Property<DurationReport, Date>();
  public static final Property<DurationReport, List<TrafficReport>> reports = new Property<DurationReport, List<TrafficReport>>();

  public static final Instantiator<DurationReport> DurationReport = new Instantiator<DurationReport>() {
    public DurationReport instantiate(PropertyLookup<DurationReport> lookup) {

      List<TrafficReport> trafficReports = lookup.valueOf(reports, asList(make(a(TrafficReportMaker.Report))));
      return com.geeksaint.traffix.DurationReport.prepare(lookup.valueOf(sessionStartTime, new Date()), trafficReports);
    }
  };

  public static DurationReport makeReport(Date sessionStartTime, TrafficReport... reports) {
    return make(a(
        DurationReportMaker.DurationReport,
        with(DurationReportMaker.reports, asList(reports)),
        with(DurationReportMaker.sessionStartTime, sessionStartTime)));
  }
}
