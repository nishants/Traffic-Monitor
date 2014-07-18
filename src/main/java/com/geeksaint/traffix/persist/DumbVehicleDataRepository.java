package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class DumbVehicleDataRepository implements VehicleDataRepository {
  private final List<VehicleData> savedVehicleData = new ArrayList<VehicleData>();

  public synchronized void save(VehicleData vehicle){
    savedVehicleData.add(vehicle);
  }

  public synchronized List<VehicleData> getAllVehiclesByTime(Date fromDate, Date toDate){
    long startTime = fromDate.getTime();
    long endTime = toDate.getTime();
    List<VehicleData> resultList = new ArrayList<VehicleData>();
    for(VehicleData vehicleData : savedVehicleData){
      long time = timeOf(vehicleData);
      if(time > endTime) break;
      if(time >= startTime){
        resultList.add(vehicleData);
      }
    }
    return resultList;
  }

  private long timeOf(VehicleData vehicleData) {
    return vehicleData.getTime().getTime();
  }
}
