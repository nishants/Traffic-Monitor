package com.geeksaint.traffix.persist;

import com.geeksaint.traffix.VehicleData;

import java.util.ArrayList;
import java.util.List;

import static com.geeksaint.traffix.util.DateSupport.timeOfDayInMinutes;
import static java.lang.Math.min;

public class DailyTrafficDataStoreStore implements TrafficDataStore {
  private final int SLOTS_PER_DAY = 288;
  private final int SLOTS_SIZE_IN_MINUTES = 5;
  private final SlotData[] slotDataList ;

  public DailyTrafficDataStoreStore(){
    slotDataList = new SlotData[SLOTS_PER_DAY];
    setupSlotData();
  }

  //Add a vehicle data
  //Throws exception if index is already built
  @Override
  public synchronized void add(VehicleData vehicleData){
    slotFor(vehicleData).add(vehicleData);
  }

  //Aggregates the traffic data at slot level.
  @Override
  public synchronized void buildIndex(){
    for(SlotData data : slotDataList){
      data.prepareReport();
    }
  }

  //returns traffic report for the duration(takes the minimum 5 minutes interval that covers the duration)
  // For e.g. if fromMinute = 113, and toMinute = 237, actual data will be form 110 mins to 240 mins
  @Override
  public TrafficReport report(int fromMinute, int toMinute){
    int fromSlotIndex = fromMinute / SLOTS_SIZE_IN_MINUTES;
    int toSlotIndex = min(toMinute / SLOTS_SIZE_IN_MINUTES, SLOTS_PER_DAY - 1);
    TrafficReport report = getSlotReport(fromSlotIndex);

    for(int i = fromSlotIndex + 1; i <=toSlotIndex; i++){
      report = report.merge(getSlotReport(i));
    }
    return report;
  }

  @Override
  public List<VehicleData> getAllVehicleData() {
    List<VehicleData> allData = new ArrayList<VehicleData>();
    for(SlotData slotData : slotDataList){
      allData.addAll(slotData.getVehicleDataList());
    }
    return allData;
  }

  private TrafficReport getSlotReport(int index) {
    return slotDataList[index].getReport();
  }

  private void setupSlotData() {
    for(int i =0; i< SLOTS_PER_DAY; i++){
      slotDataList[i] = SlotData.create();
    }
  }

  private SlotData slotFor(VehicleData vehicleData) {
    int timeInMinutes = timeOfDayInMinutes(vehicleData.getTime());
    int slotIndex = timeInMinutes / SLOTS_SIZE_IN_MINUTES;
    return slotDataList[slotIndex];
  }

  public static TrafficDataStore create() {
    return new DailyTrafficDataStoreStore();
  }
}
