package com.geeksaint.traffix.interpret;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
@Getter
@EqualsAndHashCode
@ToString
public class Reading {
  private final Date time;
  private final boolean laneA;

  protected Reading(Date time, Boolean laneA) {
    this.time = time;
    this.laneA = laneA;
  }

  public static Reading of(Date recordedAt, Boolean goingNorth) {
    return new Reading(recordedAt, goingNorth);
  }
}
