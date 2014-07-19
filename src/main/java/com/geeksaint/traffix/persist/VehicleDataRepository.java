package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.DurationReport;
import com.geeksaint.traffix.VehicleData;

public interface VehicleDataRepository{
  void save(VehicleData vehicleOne);

  //Reports like per 5 minutes everyday
  TrafficReport reportForIntervals(int minutes);

  //Reports like 08:00 to 10:10 for all days
  DurationReport report(int fromMinute, int toMinute);

  // Prepare data for reporting
  void buildIndex();
}
