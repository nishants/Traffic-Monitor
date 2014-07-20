package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.DurationReport;
import com.geeksaint.traffix.IntervalReport;
import com.geeksaint.traffix.VehicleData;

import java.util.List;

public interface DataRepository {
  void save(VehicleData vehicleOne);

  //Reports like per 5 minutes everyday
  IntervalReport reportForIntervals(int intervalSizeInMinutes);

  //Reports like 08:00 to 10:10 for all days
  DurationReport reportForDuration(int fromMinute, int toMinute);

  // Prepare data for reporting
  void buildIndex();

  List<VehicleData> unorderedAllVehicleData();
}
