package com.geeksaint.traffix;

import com.geeksaint.traffix.util.DateSupport;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

import static com.geeksaint.traffix.Lane.*;

//Represents one vehicle data
@EqualsAndHashCode
@ToString
public class VehicleData {
  @Getter
  private final float speed;
  private final Date timeAtHoseA;
  @Getter
  private final Lane lane;
  public static final float DEFAULT_AXLE_LENGTH = 2.5f;

  public VehicleData(float speed, Date timeAtHoseA, Lane lane) {
    this.speed = speed;
    this.timeAtHoseA = timeAtHoseA;
    this.lane = lane;
  }

  public int getTimeOfDayInSeconds(){
    return DateSupport.timeOfDayInSeconds(timeAtHoseA);
  }

  public static VehicleData record(List<Reading> readings) {
    float speed = readings.size() == 2 ?
        evaluateSpeedForLaneA(readings, DEFAULT_AXLE_LENGTH) :
        evaluateSpeedForLaneB(readings, DEFAULT_AXLE_LENGTH);

    Date timeAtHoseA = frontAxleHoseATime(readings);
    return new VehicleData(speed, timeAtHoseA, laneOf(readings));
  }

  private static Lane laneOf(List<Reading> readings) {
    return readings.size() == 2 ? LANE_A : LANE_B;
  }

  private static float evaluateSpeedForLaneB(List<Reading> readings, float axleLength) {
    long frontAxleHoseATime = timeInMillis(readings.get(0));
    long frontAxleHoseBTime = timeInMillis(readings.get(1));
    long backAxleHoseATime = timeInMillis(readings.get(2));
    long backAxleHoseBTime = timeInMillis(readings.get(3));

    float speedByHoseA = speed(frontAxleHoseATime, backAxleHoseATime, axleLength);
    float speedByHoseB = speed(frontAxleHoseBTime, backAxleHoseBTime, axleLength);

    return (speedByHoseA + speedByHoseB) / 2;
  }

  private static Date frontAxleHoseATime(List<Reading> readings) {
    return readings.get(0).getTime();
  }

  private static float evaluateSpeedForLaneA(List<Reading> readings, float axleLength) {
    long frontAxleTime = timeInMillis(readings.get(0));
    long backAxleTime = timeInMillis(readings.get(1));
    return speed(frontAxleTime, backAxleTime, axleLength);
  }

  private static float speed(long frontAxleTime, long backAxleTime, float axleLength) {
    float speed = axleLength / (backAxleTime - frontAxleTime);
    return speed * 1000;
  }

  private static long timeInMillis(Reading reading) {
    return reading.getTime().getTime();
  }

  public Date getTime() {
    return timeAtHoseA;
  }

  public boolean isLaneA() {
    return lane == LANE_A;
  }
}
