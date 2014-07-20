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
import static com.geeksaint.traffix.util.DateSupport.increment;
import static com.geeksaint.traffix.util.DateSupport.timeStampToMillis;
import static com.geeksaint.traffix.util.DateSupport.toDateOfYear;
import static com.geeksaint.traffix.util.DateSupport.toMinutes;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DataRepositoryTestForDurationReport {

  private VehicleDataRepository repository;
  private Date date;

  @Before
  public void setup(){
    repository = new DataRepository();
    date = toDateOfYear(3, 1, 2014);
  }

  @Test
  public void shouldGetReportForRange(){
    VehicleData vehicleZero   = vehicleWith(addDate(date, timeStampToMillis("0221")), 39f, LANE_B);
    VehicleData vehicleOne    = vehicleWith(addDate(date, timeStampToMillis("0300")), 30f, LANE_A);
    VehicleData vehicleTwo    = vehicleWith(addDate(date, timeStampToMillis("0304")), 40f, LANE_A);
    VehicleData vehicleThree  = vehicleWith(addDate(increment(date), timeStampToMillis("0315")), 25f, LANE_B);
    VehicleData vehicleFour   = vehicleWith(addDate(increment(date), timeStampToMillis("0319")), 39f, LANE_B);
    VehicleData vehicleFive   = vehicleWith(addDate(increment(date), timeStampToMillis("1421")), 39f, LANE_B);

    TrafficReport expectedReportZero  = reportFor(vehicleZero);
    TrafficReport expectedReportOne   = reportFor(vehicleOne, vehicleTwo);
    TrafficReport expectedReportTwo   = reportFor(vehicleThree, vehicleFour);
    TrafficReport expectedReportThree = reportFor(vehicleFive);
    TrafficReport expectedReportFour  = expectedReportZero.merge(expectedReportOne);

    repository.save(vehicleZero);
    repository.save(vehicleOne);
    repository.save(vehicleTwo);
    repository.save(vehicleThree);
    repository.save(vehicleFour);
    repository.save(vehicleFive);
    repository.buildIndex();

    assertThat(repository.reportForDuration(toMinutes("0300"), toMinutes("0305")).getSessionStartTime(), is(date));
    assertThat(repository.reportForDuration(toMinutes("0300"), toMinutes("0305")).getForDay(0), is(expectedReportOne));
    assertThat(repository.reportForDuration(toMinutes("0315"), toMinutes("0316")).getForDay(1), is(expectedReportTwo));
    assertThat(repository.reportForDuration(toMinutes("0220"), toMinutes("0225")).getForDay(0), is(expectedReportZero));
    assertThat(repository.reportForDuration(toMinutes("1420"), toMinutes("1425")).getForDay(1), is(expectedReportThree));
    assertThat(repository.reportForDuration(toMinutes("0200"), toMinutes("0325")).getForDay(0), is(expectedReportFour));
    assertThat(repository.reportForDuration(toMinutes("0200"), toMinutes("0325")).getForDay(1), is(expectedReportTwo));
  }
}
