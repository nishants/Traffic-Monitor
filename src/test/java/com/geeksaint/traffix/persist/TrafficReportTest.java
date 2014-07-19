package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.geeksaint.traffix.maker.LaneReportMaker.laneReportWith;
import static com.geeksaint.traffix.maker.VehicleDataMaker.vehicleWith;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TrafficReportTest {

  private List<VehicleData> vehicleDataList;
  private List<VehicleData> laneAVehicles;
  private List<VehicleData> laneBVehicles;

  @Before
  public void setup(){
    VehicleData laneAOne = vehicleWith(100l, 30f, LANE_A);
    VehicleData laneATwo = vehicleWith(200l, 40f, LANE_A);
    VehicleData laneBOne = vehicleWith(150l, 25f, LANE_B);
    VehicleData laneBTwo = vehicleWith(230l, 39f, LANE_B);
    vehicleDataList = asList(laneAOne, laneBOne, laneATwo, laneBTwo);
    laneAVehicles = asList(laneAOne,laneATwo);
    laneBVehicles = asList(laneBOne,laneBTwo);
  }

  @Test
  public void shouldCreateLaneReports(){
    LaneReport expectedLaneAReport = laneReportWith(laneAVehicles, LANE_A);
    LaneReport expectedLaneBReport = laneReportWith(laneBVehicles, LANE_B) ;

    TrafficReport trafficReport = TrafficReport.prepare(vehicleDataList);

    assertThat(trafficReport.getLaneAReport(), is(expectedLaneAReport));
    assertThat(trafficReport.getLaneBReport(), is(expectedLaneBReport));
  }
}
