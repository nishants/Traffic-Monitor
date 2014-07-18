package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;
import com.geeksaint.traffix.maker.VehicleDataMaker;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DumbVehicleDataRepositoryTest {

  private VehicleDataRepository repository;

  @Before
  public void setUp() throws Exception {
    repository = new DumbVehicleDataRepository();
  }

  @Test
  public void shouldSaveVehicleData(){
    VehicleData vehicleOne = atTime(100l);
    VehicleData vehicleTwo = atTime(200l);
    VehicleData vehicleThree = atTime(300l);
    VehicleData vehicleFour = atTime(400l);
    VehicleData vehicleFive = atTime(500l);

    repository.save(vehicleOne);
    repository.save(vehicleTwo);
    repository.save(vehicleThree);
    repository.save(vehicleFour);
    repository.save(vehicleFive);

    assertThat(repository.getAllVehiclesByTime(130l, 499l), is(asList(vehicleTwo, vehicleThree, vehicleFour)));
    assertThat(repository.getAllVehiclesByTime(100l, 500l), is(asList(vehicleOne, vehicleTwo, vehicleThree, vehicleFour, vehicleFive)));
    assertThat(repository.getAllVehiclesByTime(10l, 99l), is(Collections.<VehicleData>emptyList()));
    assertThat(repository.getAllVehiclesByTime(510l, 590l), is(Collections.<VehicleData>emptyList()));
  }

  private VehicleData atTime(long value) {
    return make(a(VehicleDataMaker.VehicleData, with(VehicleDataMaker.time, value)));
  }
}
