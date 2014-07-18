package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;

import java.util.Date;
import java.util.List;

public interface VehicleDataRepository {
  void save(VehicleData vehicleOne);
  List<VehicleData> getAllVehiclesByTime(Date startTime, Date endTime);
}
