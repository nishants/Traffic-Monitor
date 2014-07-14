package com.geeksaint.traffix;

import com.geeksaint.traffix.interpret.Reading;

import java.util.List;

//Represents one vehicle data
public class Recording {

  public Recording(List<Reading> readings) {

  }

  public static Recording record(List<Reading> readings){
    return new Recording(readings);
  }
}
