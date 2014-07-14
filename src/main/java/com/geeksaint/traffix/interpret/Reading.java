package com.geeksaint.traffix.interpret;

import lombok.Getter;

public class Reading {
  @Getter
  private final String value;

  public Reading(String value) {
    this.value = value;
  }

  public static Reading of(String value) {
    return new Reading(value);
  }
}
