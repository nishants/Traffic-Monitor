package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.VehicleData;
import com.geeksaint.traffix.input.DataSource;

import java.util.Iterator;

public class VehicleDataInterpreter implements Iterator<VehicleData>{

  private final DataSource dataSource;
  private InterpreterState state;

  public VehicleDataInterpreter(DataSource dataSource) {
    this.dataSource = dataSource;
    state = new InitialState();
  }

  @Override
  public boolean hasNext() {
    return dataSource.hasNext();
  }

  @Override
  public VehicleData next() {
    while(!state.hasOutput()){
      state = state.input(dataSource.getNext());
    }
    return state.getOutput();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

  public static VehicleDataInterpreter load(DataSource dataSource) {
    return new VehicleDataInterpreter(dataSource);
  }
}
