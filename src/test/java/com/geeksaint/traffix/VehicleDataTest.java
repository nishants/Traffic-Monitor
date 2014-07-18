package com.geeksaint.traffix;

import com.geeksaint.traffix.maker.ReadingMaker;
import org.junit.Test;

import java.util.Date;

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
        with(ReadingMaker.lane, Lane.LANE_A),
        with(ReadingMaker.time, toDate(1000l))));

    Reading backAxleHoseAReading = make(a(ReadingMaker.Reading,
        with(ReadingMaker.lane, Lane.LANE_A),
        with(ReadingMaker.time, toDate(2000l))));

    VehicleData vehicleData = VehicleData.record(asList(frontAxleHoseAReading, backAxleHoseAReading));

    assertThat(vehicleData.getSpeed(), is(2.5f));
    assertThat(vehicleData.getTime(), is(frontAxleHoseAReading.getTime()));
  }

  @Test
  public void shouldSetSpeedForLaneBVehicles() {
    Reading frontAxleHoseAReading = make(a(ReadingMaker.Reading,
        with(ReadingMaker.lane, Lane.LANE_A),
        with(ReadingMaker.time, toDate(1000l))));
    Reading frontAxleHoseBReading = make(a(ReadingMaker.Reading,
        with(ReadingMaker.lane, Lane.LANE_A),
        with(ReadingMaker.time, toDate(1003l))));

    Reading backAxleHoseAReading = make(a(ReadingMaker.Reading,
        with(ReadingMaker.lane, Lane.LANE_A),
        with(ReadingMaker.time, toDate(2005l))));

    Reading backAxleHoseBReading = make(a(ReadingMaker.Reading,
        with(ReadingMaker.lane, Lane.LANE_A),
        with(ReadingMaker.time, toDate(2013l))));

    VehicleData vehicleData = VehicleData.record(asList(
        frontAxleHoseAReading,
        frontAxleHoseBReading,
        backAxleHoseAReading,
        backAxleHoseBReading
    ));

    assertThat(vehicleData.getSpeed(), is(2.4814048f));
    assertThat(vehicleData.getTime(), is(frontAxleHoseAReading.getTime()));
  }

  private Date toDate(long time) {
    return new Date(time);
  }
}
