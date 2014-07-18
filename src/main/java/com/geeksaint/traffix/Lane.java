package com.geeksaint.traffix;

public enum Lane {
  LANE_A("A"),
  LANE_B("B");

  private String readingPrefix;

  Lane(String prefix) {
    readingPrefix = prefix;
  }

  public static Lane of(String token) {
    for (Lane lane : values()) {
      if (token.startsWith(lane.readingPrefix)) return lane;
    }
    return null;
  }
}
