package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.geeksaint.traffix.maker.VehicleDataMaker.reportFor;
import static com.geeksaint.traffix.maker.VehicleDataMaker.vehicleWith;
import static com.geeksaint.traffix.util.DateSupport.addDate;
import static com.geeksaint.traffix.util.DateSupport.timeStampToMillis;
import static com.geeksaint.traffix.util.DateSupport.toDateOfYear;
import static com.geeksaint.traffix.util.DateSupport.toMinutes;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DailyTrafficDataStoreStoreTest {
  private TrafficDataStore trafficDataStore;
  private Date date;

  @Before
  public void setUp() throws Exception {
    trafficDataStore = DailyTrafficDataStoreStore.create();
    date = toDateOfYear(3, 1, 2014);
  }

  @Test
  //When merging of reports not needed.
  public void shouldReturnCorrectReportWithoutMerging() {
    VehicleData vehicleZero   = vehicleWith(addDate(date, timeStampToMillis("0221")), 39f, LANE_B);
    VehicleData vehicleOne    = vehicleWith(addDate(date, timeStampToMillis("0300")), 30f, LANE_A);
    VehicleData vehicleTwo    = vehicleWith(addDate(date, timeStampToMillis("0304")), 40f, LANE_A);
    VehicleData vehicleThree  = vehicleWith(addDate(date, timeStampToMillis("0315")), 25f, LANE_B);
    VehicleData vehicleFour   = vehicleWith(addDate(date, timeStampToMillis("0319")), 39f, LANE_B);
    VehicleData vehicleFive   = vehicleWith(addDate(date, timeStampToMillis("1421")), 39f, LANE_B);

    TrafficReport expectedReportZero = reportFor(vehicleZero);
    TrafficReport expectedReportOne = reportFor(vehicleOne, vehicleTwo);
    TrafficReport expectedReportTwo = reportFor(vehicleThree, vehicleFour);
    TrafficReport expectedReportThree = reportFor(vehicleFive);
    TrafficReport expectedReportFour = expectedReportZero.merge(expectedReportOne).merge(expectedReportTwo);
    TrafficReport expectedReportFive =expectedReportZero.merge(expectedReportOne).merge(expectedReportTwo).merge(expectedReportThree);

    trafficDataStore.add(vehicleZero);
    trafficDataStore.add(vehicleOne);
    trafficDataStore.add(vehicleTwo);
    trafficDataStore.add(vehicleThree);
    trafficDataStore.add(vehicleFour);
    trafficDataStore.add(vehicleFive);
    trafficDataStore.buildIndex();

    assertThat(trafficDataStore.report(toMinutes("0300"), toMinutes("0305")), is(expectedReportOne));
    assertThat(trafficDataStore.report(toMinutes("0315"), toMinutes("0316")), is(expectedReportTwo));
    assertThat(trafficDataStore.report(toMinutes("0220"), toMinutes("0225")), is(expectedReportZero));
    assertThat(trafficDataStore.report(toMinutes("1420"), toMinutes("1425")), is(expectedReportThree));
    assertThat(trafficDataStore.report(toMinutes("0200"), toMinutes("0325")), is(expectedReportFour));
    assertThat(trafficDataStore.report(toMinutes("0000"), toMinutes("2400")), is(expectedReportFive));
  }

}
