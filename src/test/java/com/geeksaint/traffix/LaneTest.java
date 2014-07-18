package com.geeksaint.traffix;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LaneTest {
  @Test
  public void laneAMustBeginWithAAndLaneTwoWithB(){
    assertThat(Lane.of("A12"), is(Lane.LANE_A));
    assertThat(Lane.of("B122"), is(Lane.LANE_B));
  }
}
