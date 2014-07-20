package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.geeksaint.traffix.maker.VehicleDataMaker.reportFor;
import static com.geeksaint.traffix.maker.VehicleDataMaker.vehicleWith;
import static com.geeksaint.traffix.util.DateSupport.addDate;
import static com.geeksaint.traffix.util.DateSupport.timeStampToMillis;
import static com.geeksaint.traffix.util.DateSupport.toDateOfYear;
import static com.geeksaint.traffix.util.DateSupport.toMinutes;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DailyTrafficDataStoreStoreTest {
  private TrafficDataStore trafficDataStore;
  private Date date;
  private TrafficReport expectedReportZero;
  private TrafficReport expectedReportOne;
  private TrafficReport expectedReportTwo;
  private TrafficReport expectedReportThree;
  private TrafficReport expectedReportFour;
  private TrafficReport expectedReportFive;
  private VehicleData vehicleZero;
  private VehicleData vehicleOne;
  private VehicleData vehicleTwo;
  private VehicleData vehicleThree;
  private VehicleData vehicleFour;
  private VehicleData vehicleFive;

  @Before
  public void setUp() throws Exception {
    trafficDataStore = DailyTrafficDataStoreStore.create();
    date = toDateOfYear(3, 1, 2014);
    vehicleZero = vehicleWith(addDate(date, timeStampToMillis("0221")), 39f, LANE_B);
    vehicleOne = vehicleWith(addDate(date, timeStampToMillis("0300")), 30f, LANE_A);
    vehicleTwo = vehicleWith(addDate(date, timeStampToMillis("0304")), 40f, LANE_A);
    vehicleThree = vehicleWith(addDate(date, timeStampToMillis("0315")), 25f, LANE_B);
    vehicleFour = vehicleWith(addDate(date, timeStampToMillis("0319")), 39f, LANE_B);
    vehicleFive = vehicleWith(addDate(date, timeStampToMillis("1421")), 39f, LANE_B);

    expectedReportZero = reportFor(vehicleZero);
    expectedReportOne = reportFor(vehicleOne, vehicleTwo);
    expectedReportTwo = reportFor(vehicleThree, vehicleFour);
    expectedReportThree = reportFor(vehicleFive);
    expectedReportFour = expectedReportZero.merge(expectedReportOne).merge(expectedReportTwo);
    expectedReportFive = expectedReportZero.merge(expectedReportOne).merge(expectedReportTwo).merge(expectedReportThree);

    trafficDataStore.add(vehicleZero);
    trafficDataStore.add(vehicleOne);
    trafficDataStore.add(vehicleTwo);
    trafficDataStore.add(vehicleThree);
    trafficDataStore.add(vehicleFour);
    trafficDataStore.add(vehicleFive);
    trafficDataStore.buildIndex();
  }

  @Test
  public void shouldReturnReport() {
    assertThat(trafficDataStore.report(toMinutes("0300"), toMinutes("0305")), is(expectedReportOne));
    assertThat(trafficDataStore.report(toMinutes("0315"), toMinutes("0316")), is(expectedReportTwo));
    assertThat(trafficDataStore.report(toMinutes("0220"), toMinutes("0225")), is(expectedReportZero));
    assertThat(trafficDataStore.report(toMinutes("1420"), toMinutes("1425")), is(expectedReportThree));
    assertThat(trafficDataStore.report(toMinutes("0200"), toMinutes("0325")), is(expectedReportFour));
    assertThat(trafficDataStore.report(toMinutes("0000"), toMinutes("2400")), is(expectedReportFive));
  }

  @Test
  public void shouldReturnVehicleReport(){
    List<VehicleData> allData = asList(vehicleZero, vehicleOne, vehicleTwo, vehicleThree, vehicleFour, vehicleFive);
    assertThat(trafficDataStore.getAllVehicleData(), is(allData));
  }
}
