package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.geeksaint.traffix.maker.TrafficReportMaker.Report;
import static com.geeksaint.traffix.maker.TrafficReportMaker.vehicleData;
import static com.geeksaint.traffix.maker.VehicleDataMaker.vehicleWith;
import static com.geeksaint.traffix.util.DateSupport.toDateOfYear;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DailyTrafficDataTest {
  private DailyTrafficData trafficData;
  private Date date;

  @Before
  public void setUp() throws Exception {
    trafficData = new DailyTrafficData(3, 1, 2014);
    date = toDateOfYear(3, 1, 2014);
  }

  @Test
  //When merging of reports not needed.
  public void shouldReturnCorrectReportWithoutMerging() {
    VehicleData vehicleOne    = vehicleWith(addDate(date, timeStampToMillis("0300")), 30f, LANE_A);
    VehicleData vehicleTwo    = vehicleWith(addDate(date, timeStampToMillis("0304")), 40f, LANE_A);
    VehicleData vehicleThree  = vehicleWith(addDate(date, timeStampToMillis("0315")), 25f, LANE_B);
    VehicleData vehicleFour   = vehicleWith(addDate(date, timeStampToMillis("0319")), 39f, LANE_B);
    VehicleData vehicleFive   = vehicleWith(addDate(date, timeStampToMillis("0221")), 39f, LANE_B);
    VehicleData vehicleSix    = vehicleWith(addDate(date, timeStampToMillis("1421")), 39f, LANE_B);

    TrafficReport expectedReportOne = reportFor(vehicleOne, vehicleTwo);
    TrafficReport expectedReportTwo = reportFor(vehicleThree, vehicleFour);
    TrafficReport expectedReportThree = reportFor(vehicleFive);
    TrafficReport expectedReportFour = reportFor(vehicleSix);

    trafficData.add(vehicleOne);
    trafficData.add(vehicleTwo);
    trafficData.add(vehicleThree);
    trafficData.add(vehicleFour);
    trafficData.add(vehicleFive);
    trafficData.add(vehicleSix);
    trafficData.buildIndex();

    assertThat(trafficData.report(toMinutes("0300"), toMinutes("0315")), is(expectedReportOne));
    assertThat(trafficData.report(toMinutes("0315"), toMinutes("0316")), is(expectedReportTwo));
    assertThat(trafficData.report(toMinutes("0220"), toMinutes("0225")), is(expectedReportThree));
    assertThat(trafficData.report(toMinutes("1420"), toMinutes("1425")), is(expectedReportFour));
  }

  private TrafficReport reportFor(VehicleData...vehicleDataList) {
    return make(a(Report, with(vehicleData, asList(vehicleDataList))));
  }

  private long addDate(Date date, long timeStampToMillis) {
    return new Date(date.getTime() + timeStampToMillis).getTime();
  }

  private long timeStampToMillis(String timeStampInHHMM) {
    long minutes = toMinutes(timeStampInHHMM);
    return minutes * 60 * 1000;
  }

  //Converts time into minutes since 00:00
  private int toMinutes(String timeStampInMMSS) {
    int minutes = minuteOf(timeStampInMMSS);
    minutes += (hourOf(timeStampInMMSS) * 60);
    return minutes;
  }

  private int minuteOf(String timeStampInMMSS) {
    return parseInt(timeStampInMMSS.substring(2, 4));
  }

  private int hourOf(String timeStampInMMSS) {
    return parseInt(timeStampInMMSS.substring(0, 2));
  }
}
