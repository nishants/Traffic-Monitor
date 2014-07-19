package com.geeksaint.traffix;

import com.geeksaint.traffix.maker.ReadingMaker;
import com.geeksaint.traffix.maker.VehicleDataMaker;
import com.geeksaint.traffix.util.DateSupport;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static com.geeksaint.traffix.Lane.*;
import static com.geeksaint.traffix.util.DateSupport.timeOfDayInSeconds;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class VehicleDataTest {
  @Test
  public void shouldSetSpeedForLaneAVehicles() {
    Reading frontAxleHoseAReading = make(a(ReadingMaker.Reading,
        with(ReadingMaker.lane, LANE_A),
        with(ReadingMaker.time, toDate(1000l))));

    Reading backAxleHoseAReading = make(a(ReadingMaker.Reading,
        with(ReadingMaker.lane, LANE_A),
        with(ReadingMaker.time, toDate(2000l))));

    VehicleData vehicleData = VehicleData.record(asList(frontAxleHoseAReading, backAxleHoseAReading));

    assertThat(vehicleData.getSpeed(), is(2.5f));
    assertThat(vehicleData.getLane(), is(LANE_A));
    assertThat(vehicleData.isLaneA(), is(true));
    assertThat(vehicleData.getTime(), is(frontAxleHoseAReading.getTime()));
  }

  @Test
  public void shouldSetSpeedForLaneBVehicles() {
    Reading frontAxleHoseAReading = make(a(ReadingMaker.Reading,
        with(ReadingMaker.lane, LANE_A),
        with(ReadingMaker.time, toDate(1000l))));
    Reading frontAxleHoseBReading = make(a(ReadingMaker.Reading,
        with(ReadingMaker.lane, LANE_A),
        with(ReadingMaker.time, toDate(1003l))));

    Reading backAxleHoseAReading = make(a(ReadingMaker.Reading,
        with(ReadingMaker.lane, LANE_A),
        with(ReadingMaker.time, toDate(2005l))));

    Reading backAxleHoseBReading = make(a(ReadingMaker.Reading,
        with(ReadingMaker.lane, LANE_A),
        with(ReadingMaker.time, toDate(2013l))));

    VehicleData vehicleData = VehicleData.record(asList(
        frontAxleHoseAReading,
        frontAxleHoseBReading,
        backAxleHoseAReading,
        backAxleHoseBReading
    ));

    assertThat(vehicleData.getSpeed(), is(2.4814048f));
    assertThat(vehicleData.getLane(), is(LANE_B));
    assertThat(vehicleData.isLaneA(), is(false));
    assertThat(vehicleData.getTime(), is(frontAxleHoseAReading.getTime()));
  }

  @Test
  public void shouldReturnTimeInSeconds(){
    Calendar calendar = Calendar.getInstance();
    calendar.set(2014, 1, 3, 1, 1, 1);
    Date time = calendar.getTime();
    VehicleData vehicleData = make(a(VehicleDataMaker.VehicleData, with(VehicleDataMaker.time, time.getTime())));
    int expectedTime = timeOfDayInSeconds(calendar.getTime());
    assertThat(vehicleData.getTimeOfDayInSeconds(), is(expectedTime));
  }

  private Date toDate(long time) {
    return new Date(time);
  }
}
