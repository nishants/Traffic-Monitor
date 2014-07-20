package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.DurationReport;
import com.geeksaint.traffix.IntervalReport;
import com.geeksaint.traffix.VehicleData;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.geeksaint.traffix.util.DateSupport.toDateOfYear;
import static com.geeksaint.traffix.util.DateSupport.toDateStamp;

public class DataRepositoryImpl implements DataRepository {
  private final Map<String,TrafficDataStore> dayWiseData = new LinkedHashMap<String, TrafficDataStore>();
  private Date sessionStartTime;

  @Override
  public synchronized void save(VehicleData vehicle){
    String day = toDateStamp(vehicle.getTime());
    checkDataExistsFor(day);
    adjustStartTime(vehicle.getTime());
    getStoreFor(day).add(vehicle);
  }

  private void adjustStartTime(Date time) {
    if(sessionStartTime == null || time.before(sessionStartTime)){
      sessionStartTime = toDateOfYear(time);
    }
  }

  @Override
  public IntervalReport reportForIntervals(int intervalInMinutes) {
    checkForMultipleOfFive(intervalInMinutes);
    int numberOfIntervals = 24 * 60 /intervalInMinutes;
    List<DurationReport> durationReports = new ArrayList<DurationReport>();
    for(int interval = 0; interval < numberOfIntervals; interval++){
      durationReports.add(reportForDuration(interval * intervalInMinutes, (interval + 1) * intervalInMinutes));
    }

    return IntervalReport.prepareFrom(durationReports);
  }

  private void checkForMultipleOfFive(int minutes) {
    if (minutes % 5 != 0) {
      throw new UnsupportedOperationException("Interval must be in minutes and a multiple of 5");
    }
  }

  @Override
  public void buildIndex() {
    for(TrafficDataStore trafficDataStore : dayWiseData.values()){
      trafficDataStore.buildIndex();
    }
  }

  @Override
  public DurationReport reportForDuration(int fromMinute, int toMinute) {
    List<TrafficReport> reports = new ArrayList<TrafficReport>();
    for(TrafficDataStore trafficDataStore : dayWiseData.values()){
      reports.add(trafficDataStore.report(fromMinute, toMinute));
    }
    return DurationReport.prepare(sessionStartTime, reports);
  }

  private void checkDataExistsFor(String day) {
    if(getStoreFor(day) == null){
      dayWiseData.put(day, DailyTrafficDataStoreStore.create());
    }
  }

  private TrafficDataStore getStoreFor(String day) {
    return dayWiseData.get(day);
  }
}
