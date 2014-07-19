package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.Lane;
import com.geeksaint.traffix.VehicleData;
import com.geeksaint.traffix.maker.LaneReportMaker;
import com.geeksaint.traffix.maker.VehicleDataMaker;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
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

  private LaneReport laneReportWith(List<VehicleData> laneAVehicles1, Lane laneA) {
    return make(a(LaneReportMaker.LaneReport,
        with(LaneReportMaker.vehicleData, laneAVehicles1),
        with(LaneReportMaker.lane, laneA)));
  }

  private VehicleData vehicleWith(long time, float speed, Lane lane) {
    return make(a(VehicleDataMaker.VehicleData,
        with(VehicleDataMaker.time, time),
        with(VehicleDataMaker.speed, speed),
        with(VehicleDataMaker.lane, lane)));
  }
}
