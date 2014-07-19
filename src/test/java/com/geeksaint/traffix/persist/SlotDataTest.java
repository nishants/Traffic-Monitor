package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static com.geeksaint.traffix.maker.TrafficReportMaker.*;
import static com.geeksaint.traffix.maker.VehicleDataMaker.VehicleData;
import static com.geeksaint.traffix.maker.VehicleDataMaker.speed;
import static com.geeksaint.traffix.maker.VehicleDataMaker.time;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SlotDataTest {

  private SlotData slotData;
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    slotData = SlotData.create();
  }

  @Test
  public void shouldPrepareReport(){
    VehicleData vehicleDataOne = make(a(VehicleData, with(time, 100l), with(speed, 100f)));
    VehicleData vehicleDataTwo = make(a(VehicleData, with(time, 100l), with(speed, 100f)));
    VehicleData vehicleDataThree = make(a(VehicleData, with(time, 100l), with(speed, 100f)));
    VehicleData vehicleDataFour = make(a(VehicleData, with(time, 100l), with(speed, 100f)));

    slotData.add(vehicleDataOne);
    slotData.add(vehicleDataTwo);
    slotData.add(vehicleDataThree);
    slotData.add(vehicleDataFour);

    List<VehicleData> vehicleDataList = asList(vehicleDataOne, vehicleDataTwo, vehicleDataThree, vehicleDataFour);
    TrafficReport expectedReport = make(a(Report, with(vehicleData, vehicleDataList)));

    slotData.prepareReport();

    assertThat(slotData.getReport(), is(expectedReport));
  }


  @Test
  public void shouldThrowExceptionOnAddingDataAfterBuildingReport(){
    expectedException.expect(IllegalStateException.class);
    expectedException.expectMessage("Cannot add data after creating index");
    slotData.prepareReport();
    slotData.add(make(a(VehicleData)));
  }
}
