package com.geeksaint.traffix;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

import static com.geeksaint.traffix.Lane.*;

@Getter
@EqualsAndHashCode
@ToString
//Represents a reading form the source eg. A12322 or B542323
public class Reading {
  private final Date time;
  private final Lane lane;

  protected Reading(Date time, Lane lane) {
    this.time = time;
    this.lane = lane;
  }

  public static Reading of(Date recordedAt, Lane goingNorth) {
    return new Reading(recordedAt, goingNorth);
  }

  public boolean isHoseA() {
    return lane.equals(LANE_A);
  }
}
