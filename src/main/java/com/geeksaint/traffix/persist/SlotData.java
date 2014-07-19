package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.geeksaint.traffix.persist.TrafficReport.prepare;

@Getter
public class SlotData {
  private static final String REPORT_ALREADY_EXISTS_MESSAGE = "Cannot add data after creating index";

  //Built on the calling the #prepareReport()
  private TrafficReport report;
  private final List<VehicleData> vehicleDataList = new ArrayList<VehicleData>();
  private SlotData(){}

  public synchronized void prepareReport(){
    report = prepare(vehicleDataList);
  }
  public synchronized void add(VehicleData vehicleData) {
    checkForReport();
    vehicleDataList.add(vehicleData);
  }

  private void checkForReport() {
    if(report != null){
      throw  new IllegalStateException(REPORT_ALREADY_EXISTS_MESSAGE);
    }
  }

  public static SlotData create() {
    return new SlotData();
  }
}
