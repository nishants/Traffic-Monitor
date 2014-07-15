package com.geeksaint.traffix.interpret;

import lombok.Getter;

import java.util.Date;

@Getter
public class Reading {
  private final Date time;
  private final boolean ofPointA;

  protected Reading(Date time, Boolean ofPointA) {
    this.time = time;
    this.ofPointA = ofPointA;
  }

  public static Reading of(Date recordedAt, Boolean goingNorth) {
    return new Reading(recordedAt, goingNorth);
  }
}
