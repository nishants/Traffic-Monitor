package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;

public interface VehicleDataRepository extends TrafficData{
  void save(VehicleData vehicleOne);
  TrafficReport reportForIntervals(int minutes);
}
