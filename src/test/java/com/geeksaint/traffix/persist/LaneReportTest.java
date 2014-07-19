package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;
import org.junit.Test;

import java.util.List;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.maker.VehicleDataMaker.VehicleData;
import static com.geeksaint.traffix.maker.VehicleDataMaker.speed;
import static com.geeksaint.traffix.maker.VehicleDataMaker.time;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LaneReportTest {
  @Test
  public void shouldCalculateStatistics(){
    VehicleData vehicleDataOne = make(a(VehicleData, with(time, 100l), with(speed, 100f)));
    VehicleData vehicleDataTwo = make(a(VehicleData, with(time, 200l), with(speed, 150f)));
    VehicleData vehicleDataThree = make(a(VehicleData, with(time, 300l), with(speed, 140f)));
    VehicleData vehicleDataFour = make(a(VehicleData, with(time, 400l), with(speed, 120f)));
    List<VehicleData> vehicleDataList = asList(vehicleDataOne, vehicleDataTwo, vehicleDataThree, vehicleDataFour);

    LaneReport laneReport = LaneReport.prepareFor(vehicleDataList, LANE_A);

    assertThat(laneReport.getLane(), is(LANE_A));
    assertThat(laneReport.getSumOfSpeeds(), is(510.0));
    assertThat(laneReport.getVehicleCount(), is(4l));
    assertThat(laneReport.getSumOfDistances(), is(39l));
  }
}
