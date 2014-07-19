package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.DurationReport;
import com.geeksaint.traffix.VehicleData;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.geeksaint.traffix.util.DateSupport.toDateOfYear;
import static com.geeksaint.traffix.util.DateSupport.toDateStamp;

public class DataRepository implements VehicleDataRepository {
  private final Map<String,TrafficData> dayWiseData = new LinkedHashMap<String, TrafficData>();
  private Date sessionStartTime;

  @Override
  public synchronized void save(VehicleData vehicle){
    String day = toDateStamp(vehicle.getTime());
    checkDataExistsFor(day);
    adustStartTime(vehicle.getTime());
    getStoreFor(day).add(vehicle);
  }

  private void adustStartTime(Date time) {
    if(sessionStartTime == null || time.before(sessionStartTime)){
      sessionStartTime = toDateOfYear(time);
    }
  }

  @Override
  public TrafficReport reportForIntervals(int minutes) {
    return null;
  }

  @Override
  public void buildIndex() {
    for(TrafficData trafficData : dayWiseData.values()){
      trafficData.buildIndex();
    }
  }

  @Override
  public DurationReport report(int fromMinute, int toMinute) {
    List<TrafficReport> reports = new ArrayList<TrafficReport>();
    for(TrafficData trafficData : dayWiseData.values()){
      reports.add(trafficData.report(fromMinute, toMinute));
    }
    return DurationReport.prepare(sessionStartTime, reports);
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
