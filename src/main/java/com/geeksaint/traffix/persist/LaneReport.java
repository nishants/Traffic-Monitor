package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.Lane;
import com.geeksaint.traffix.VehicleData;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
//Represents the report of a lane of road
public class LaneReport {
  private final Lane lane;
  private final long vehicleCount;
  private final double sumOfSpeeds;

  //Sum of distance between vehicles
  final long sumOfDistances;

  public LaneReport merge(LaneReport withReport) {
    long vehicleCount = getVehicleCount() + withReport.getVehicleCount();
    double sumOfSpeeds = getSumOfSpeeds() + withReport.getSumOfSpeeds();
    long sumOfDistances = getSumOfDistances() + withReport.getSumOfDistances();
    return new LaneReport(lane, vehicleCount, sumOfSpeeds, sumOfDistances);
  }

  public static LaneReport prepareFor(List<VehicleData> vehicleDataList, Lane lane){
    double sumOfSpeeds = addSpeedsOf(vehicleDataList);
    long sumOfDistances = addDistancesBetween(vehicleDataList);
    return new LaneReport(lane, vehicleDataList.size(), sumOfSpeeds , sumOfDistances);
  }

  private static long addDistancesBetween(List<VehicleData> vehicleDataList) {
    long distance = 0l;
    for(int i = 1; i< vehicleDataList.size(); i ++){
      distance += (long) distanceBetweenVehicles(vehicleDataList.get(i - 1), vehicleDataList.get(i));
    }
    return distance;
  }

  //Calculates the time difference between vehicles at hose A, and calculates the distance traveled by vehicle one in period.
  private static float distanceBetweenVehicles(VehicleData leadingVehicle, VehicleData trailingVehicle) {
    long timeOfLeadingVehicle = leadingVehicle.getTime().getTime();
    long timeOfTrailingVehicle = trailingVehicle.getTime().getTime();
    long timeDifferenceInMillis = timeOfTrailingVehicle - timeOfLeadingVehicle;

    float speedOfLeadingVehicle = leadingVehicle.getSpeed();

    return timeDifferenceInMillis * speedOfLeadingVehicle / 1000;
  }

  private static double addSpeedsOf(List<VehicleData> vehicleDataList) {
    double sum = 0;
    for(VehicleData vehicleData : vehicleDataList){
      sum += vehicleData.getSpeed();
    }
    return sum;
  }
}
