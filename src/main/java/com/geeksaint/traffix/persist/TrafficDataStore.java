package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;

public interface TrafficDataStore {
  // Add a vehicle data
  //May throw exception if index is already built
  void add(VehicleData vehicleData);

  // Prepare data for reporting
  void buildIndex();

  //Prepares report for the specified duration
  TrafficReport report(int fromMinute, int toMinute);
}
