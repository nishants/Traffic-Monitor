package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SlotData {
  //Built on the calling the #prepareReport()
  private TrafficReport report;
  private final List<VehicleData> vehicleDataList = new ArrayList<VehicleData>();

  public synchronized void prepareReport(){}
  public synchronized void add(VehicleData vehicleData) {}
}
