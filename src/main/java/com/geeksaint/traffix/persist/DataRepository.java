package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;

import java.util.HashMap;
import java.util.Map;

import static com.geeksaint.traffix.persist.TrafficReport.emptyReport;
import static com.geeksaint.traffix.util.DateSupport.toDateStamp;

public class DataRepository implements VehicleDataRepository {
  private final Map<String,TrafficData> dayWiseData = new HashMap<String, TrafficData>();

  @Override
  public synchronized void save(VehicleData vehicle){
    String day = toDateStamp(vehicle.getTime());
    checkDataExistsFor(day);
    getStoreFor(day).add(vehicle);
  }

  @Override
  public TrafficReport reportForIntervals(int minutes) {
    return null;
  }

  @Override
  public void add(VehicleData vehicleData) {
    save(vehicleData);
  }

  @Override
  public void buildIndex() {
    for(TrafficData trafficData : dayWiseData.values()){
      trafficData.buildIndex();
    }
  }

  @Override
  public TrafficReport report(int fromMinute, int toMinute) {
    TrafficReport report = emptyReport();
    for(TrafficData trafficData : dayWiseData.values()){
      report = report.merge(trafficData.report(fromMinute, toMinute));
    }
    return report;
  }

  private void checkDataExistsFor(String day) {
    if(getStoreFor(day) == null){
      dayWiseData.put(day, DailyTrafficData.create());
    }
  }

  private TrafficData getStoreFor(String day) {
    return dayWiseData.get(day);
  }
}
