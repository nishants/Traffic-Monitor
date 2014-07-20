package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.IntervalReport;
import com.geeksaint.traffix.VehicleData;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.geeksaint.traffix.maker.VehicleDataMaker.reportFor;
import static com.geeksaint.traffix.maker.VehicleDataMaker.vehicleWith;
import static com.geeksaint.traffix.util.DateSupport.addDate;
import static com.geeksaint.traffix.util.DateSupport.increment;
import static com.geeksaint.traffix.util.DateSupport.timeStampToMillis;
import static com.geeksaint.traffix.util.DateSupport.toDateOfYear;
import static com.geeksaint.traffix.util.DateSupport.toMinutes;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DataRepositoryTestForIntervalReport {

  private VehicleDataRepository repository;
  private Date date;
  @Rule
  public ExpectedException expectedException = ExpectedException.none();
  private TrafficReport expectedReportZero;
  private TrafficReport expectedReportOne;
  private TrafficReport expectedReportTwo;
  private TrafficReport expectedReportThree;
  private TrafficReport expectedReportFour;
  private TrafficReport expectedReportFive;

  @Before
  public void setup(){
    repository = new DataRepository();
    date = toDateOfYear(3, 1, 2014);
    VehicleData vehicleZero   = vehicleWith(addDate(date, timeStampToMillis("0605")), 39f, LANE_B);
    VehicleData vehicleOne    = vehicleWith(addDate(date, timeStampToMillis("1300")), 30f, LANE_A);
    VehicleData vehicleTwo    = vehicleWith(addDate(date, timeStampToMillis("2300")), 40f, LANE_A);
    VehicleData vehicleThree  = vehicleWith(addDate(increment(date), timeStampToMillis("0605")), 25f, LANE_B);
    VehicleData vehicleFour   = vehicleWith(addDate(increment(date), timeStampToMillis("1300")), 39f, LANE_B);
    VehicleData vehicleFive   = vehicleWith(addDate(increment(date), timeStampToMillis("2300")), 39f, LANE_B);

    expectedReportZero = reportFor(vehicleZero);
    expectedReportOne = reportFor(vehicleOne);
    expectedReportTwo = reportFor(vehicleTwo);
    expectedReportThree = reportFor(vehicleThree);
    expectedReportFour = reportFor(vehicleFour);
    expectedReportFive = reportFor(vehicleFive);

    repository.save(vehicleZero);
    repository.save(vehicleOne);
    repository.save(vehicleTwo);
    repository.save(vehicleThree);
    repository.save(vehicleFour);
    repository.save(vehicleFive);
    repository.buildIndex();
  }

  @Test
  public void intervalSizeMustBeMultipleOfFive(){
    expectedException.expect(UnsupportedOperationException.class);
    repository.reportForIntervals(7);
  }

  @Test
  public void shouldGetReportForInterval(){
    IntervalReport intervalReport = repository.reportForIntervals(8 * 60);

    assertThat(intervalReport.getForDay(0).get(0), is(expectedReportZero));
    assertThat(intervalReport.getForDay(0).get(1), is(expectedReportOne));
    assertThat(intervalReport.getForDay(0).get(2), is(expectedReportTwo));

    assertThat(intervalReport.getForDay(1).get(0), is(expectedReportThree));
    assertThat(intervalReport.getForDay(1).get(1), is(expectedReportFour));
    assertThat(intervalReport.getForDay(1).get(2), is(expectedReportFive));
  }
}
