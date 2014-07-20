package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.geeksaint.traffix.maker.VehicleDataMaker.vehicleWith;
import static com.geeksaint.traffix.util.DateSupport.addDate;
import static com.geeksaint.traffix.util.DateSupport.increment;
import static com.geeksaint.traffix.util.DateSupport.timeStampToMillis;
import static com.geeksaint.traffix.util.DateSupport.toDateOfYear;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DataRepositoryTestForgetVehicles {

  private DataRepository repository;
  private Date date;

  @Before
  public void setup(){
    repository = new DataRepositoryImpl();
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

    repository.save(vehicleZero);
    repository.save(vehicleOne);
    repository.save(vehicleTwo);
    repository.save(vehicleThree);
    repository.save(vehicleFour);
    repository.save(vehicleFive);
    repository.buildIndex();

    assertThat(repository.unorderedAllVehicleData(), is(asList(vehicleZero, vehicleOne, vehicleTwo, vehicleThree, vehicleFour, vehicleFive)));
  }
}
