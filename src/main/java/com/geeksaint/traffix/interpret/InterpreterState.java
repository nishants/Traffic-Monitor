package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Reading;
import com.geeksaint.traffix.VehicleData;

public interface InterpreterState {
  //Takes the input and returns the next state of the interpreter
  InterpreterState input(Reading reading);

  //Returns true if the current state can have an output
  boolean hasOutput();

  //Returns output or null
  VehicleData getOutput();
}
