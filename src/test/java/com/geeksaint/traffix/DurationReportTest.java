package com.geeksaint.traffix;

import com.geeksaint.traffix.maker.DurationReportMaker;
import com.geeksaint.traffix.maker.TrafficReportMaker;
import com.geeksaint.traffix.persist.TrafficReport;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.geeksaint.traffix.maker.DurationReportMaker.makeReport;
import static com.geeksaint.traffix.maker.VehicleDataMaker.vehicleWith;
import static com.geeksaint.traffix.util.DateSupport.timeStampToMillis;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DurationReportTest {

  private TrafficReport reportOne;
  private TrafficReport reportTwo;
  private Date date;
  private DurationReport durationReport;

  @Before
  public void setup(){
    date = new Date();
    VehicleData vehicleOne = vehicleWith(timeStampToMillis("0221"), 39f, LANE_B);
    VehicleData vehicleTwo = vehicleWith(timeStampToMillis("0300"), 30f, LANE_A);

    reportOne = make(a(TrafficReportMaker.Report, with(TrafficReportMaker.vehicleData, asList(vehicleOne))));
    reportTwo = make(a(TrafficReportMaker.Report, with(TrafficReportMaker.vehicleData, asList(vehicleTwo))));

    durationReport = makeReport(date, reportOne, reportTwo);
  }

  @Test
  public void shouldMakeReports(){
    assertThat(durationReport.getSessionStartTime(), is(date));
    assertThat(durationReport.getReports(), is(asList(reportOne, reportTwo)));
    assertThat(durationReport.getForDay(0), is(reportOne));
    assertThat(durationReport.getForDay(1), is(reportTwo));
  }
}
