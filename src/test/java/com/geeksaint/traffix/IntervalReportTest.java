package com.geeksaint.traffix;

import com.geeksaint.traffix.persist.TrafficReport;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.geeksaint.traffix.maker.DurationReportMaker.makeReport;
import static com.geeksaint.traffix.maker.TrafficReportMaker.*;
import static com.geeksaint.traffix.maker.VehicleDataMaker.vehicleWith;
import static com.geeksaint.traffix.util.DateSupport.addDate;
import static com.geeksaint.traffix.util.DateSupport.increment;
import static com.geeksaint.traffix.util.DateSupport.timeStampToMillis;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IntervalReportTest {
  private Date date;
  private IntervalReport intervalReport;
  private TrafficReport reportOne;
  private TrafficReport reportTwo;
  private TrafficReport reportThree;
  private TrafficReport reportFour;
  private TrafficReport reportFive;
  private TrafficReport reportSix;


  @Before
  public void setup(){
    date = new Date();
    VehicleData vehicleOne    = vehicleWith(timeStampToMillis("0300"), 39f, LANE_B);
    VehicleData vehicleTwo    = vehicleWith(timeStampToMillis("1800"), 30f, LANE_A);
    VehicleData vehicleThree  = vehicleWith(timeStampToMillis("2300"), 30f, LANE_A);
    VehicleData vehicleFour   = vehicleWith(addDate(increment(date), timeStampToMillis("0400")), 25f, LANE_B);
    VehicleData vehicleFive   = vehicleWith(addDate(increment(date), timeStampToMillis("1900")), 39f, LANE_A);
    VehicleData vehicleSix    = vehicleWith(addDate(increment(date), timeStampToMillis("2300")), 39f, LANE_A);

    reportOne = reportOf(vehicleOne);
    reportTwo = reportOf(vehicleTwo);
    reportThree = reportOf(vehicleThree);
    reportFour = reportOf(vehicleFour);
    reportFive = reportOf(vehicleFive);
    reportSix = reportOf(vehicleSix);

    DurationReport durationReportOne = makeReport(date, reportOne, reportFour);
    DurationReport durationReportTwo = makeReport(date, reportTwo, reportFive);
    DurationReport durationReportThree = makeReport(date, reportThree, reportSix);

    intervalReport = IntervalReport.prepareFrom(asList(durationReportOne, durationReportTwo, durationReportThree));
  }

  private TrafficReport reportOf(VehicleData vehicleOne) {
    return make(a(Report, with(vehicleData, asList(vehicleOne))));
  }

  @Test
  public void shouldCreateReport(){
    assertThat(intervalReport.getSessionStartTime(), is(date));
    assertThat(intervalReport.getForDay(0), is(asList(reportOne, reportTwo, reportThree)));
    assertThat(intervalReport.getForDay(1), is(asList(reportFour, reportFive, reportSix)));
    assertThat(intervalReport.getReports(), is(asList(asList(reportOne, reportTwo, reportThree), asList(reportFour, reportFive, reportSix))));
  }
}
