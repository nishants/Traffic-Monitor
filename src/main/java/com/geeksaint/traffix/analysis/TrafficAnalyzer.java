package com.geeksaint.traffix.analysis;

import com.geeksaint.traffix.VehicleData;
import com.geeksaint.traffix.persist.VehicleDataRepository;

import java.util.Date;
import java.util.List;

public class TrafficAnalyzer {
  private final VehicleDataRepository repository;

  public TrafficAnalyzer(VehicleDataRepository vehicleDataRepository){
    repository = vehicleDataRepository;
  }

  public TrafficReport getTrafficReport(Date fromDate, Date toDate){
    List<VehicleData> vehicleData = repository.getAllVehiclesByTime(fromDate, toDate);
    return TrafficReport.of(vehicleData);
  }
}
