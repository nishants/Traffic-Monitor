package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;
import org.junit.Before;
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

  private LaneReport laneReport;
  private List<VehicleData> vehicleDataList;

  @Before
  public void setup(){
    VehicleData vehicleDataOne = make(a(VehicleData, with(time, 100l), with(speed, 100f)));
    VehicleData vehicleDataTwo = make(a(VehicleData, with(time, 200l), with(speed, 150f)));
    VehicleData vehicleDataThree = make(a(VehicleData, with(time, 300l), with(speed, 140f)));
    VehicleData vehicleDataFour = make(a(VehicleData, with(time, 400l), with(speed, 120f)));
    vehicleDataList = asList(vehicleDataOne, vehicleDataTwo, vehicleDataThree, vehicleDataFour);
    laneReport = LaneReport.prepareFor(vehicleDataList, LANE_A);
  }

  @Test
  public void shouldCalculateStatistics(){
    assertThat(laneReport.getLane(), is(LANE_A));
    assertThat(laneReport.getSumOfSpeeds(), is(510.0));
    assertThat(laneReport.getVehicleCount(), is(4l));
    assertThat(laneReport.getSumOfDistances(), is(39l));
  }

  @Test
  public void shouldMergeWithReport(){
    VehicleData vehicleDataOne    = make(a(VehicleData, with(time, 1100l), with(speed, 100f)));
    VehicleData vehicleDataTwo    = make(a(VehicleData, with(time, 1200l), with(speed, 150f)));
    VehicleData vehicleDataThree  = make(a(VehicleData, with(time, 1300l), with(speed, 140f)));
    VehicleData vehicleDataFour   = make(a(VehicleData, with(time, 1400l), with(speed, 120f)));

    List<VehicleData> vehicleDataList = asList(vehicleDataOne, vehicleDataTwo, vehicleDataThree, vehicleDataFour);
    LaneReport otherReport = LaneReport.prepareFor(vehicleDataList, LANE_A);

    LaneReport mergedReport = laneReport.merge(otherReport);

    assertThat(mergedReport.getLane(), is(LANE_A));
    assertThat(mergedReport.getSumOfSpeeds(), is(1020.0));
    assertThat(mergedReport.getVehicleCount(), is(8l));
    assertThat(mergedReport.getSumOfDistances(), is(78l));
  }
}
