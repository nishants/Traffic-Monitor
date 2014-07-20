package com.geeksaint.traffix.analysis;

import com.geeksaint.traffix.VehicleData;
import com.geeksaint.traffix.persist.DataRepository;
import com.geeksaint.traffix.persist.TrafficReport;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//Interface to the API
@AllArgsConstructor
public class Analyzer {
  private final DataRepository dataRepository;

  public final static int MORNING_START_TIME_IN_MINUTES = 6 * 60;
  public final static int MORNING_END_TIME_IN_MINUTES   = 10 * 60;
  public final static int EVENING_START_TIME_IN_MINUTES = 18 * 60;
  public final static int EVENING_END_TIME_IN_MINUTES   = 22 * 60;

  // List containing morning traffic reports, each element representing one day
  public List<TrafficReport> morningTraffic(){
    return dataRepository.reportForDuration(
        MORNING_START_TIME_IN_MINUTES,
        MORNING_END_TIME_IN_MINUTES
    ).getReports();
  }

  // List containing evening traffic reports, each element representing one day
  public List<TrafficReport> eveningTraffic(){
    return dataRepository.reportForDuration(
        EVENING_START_TIME_IN_MINUTES,
        EVENING_END_TIME_IN_MINUTES
    ).getReports();
  }

  // Each element representing ordered list of durations for one day
  // value = hourlyTrafficReport()
  // value.get(0) : report for day 1
  // value.get(0).get(3) : report for day 1, fourth hour of the day
  public List<List<TrafficReport>> hourlyTrafficReport(){
    return dataRepository.reportForIntervals(60).getReports();
  }

  // Each element representing ordered list of durations for one day
  // value = halfHourlyTrafficReport()
  // value.get(0) : report for day 1
  // value.get(0).get(3) : report for day 1, fourth half-hour of the day
  public List<List<TrafficReport>> halfHourlyTrafficReport(){
    return dataRepository.reportForIntervals(30).getReports();
  }


  // Each element representing ordered list of durations for one day
  // value = reportPer20Minutes()
  // value.get(0) : report for day 1
  // value.get(0).get(3) : report for day 1, 01:00 to 01:20(i.e. fourth 20 min duration)
  public List<List<TrafficReport>> reportPer20Minutes(){
    return dataRepository.reportForIntervals(20).getReports();
  }

  // Each element representing ordered list of durations for one day
  // value = reportPer15Minutes()
  // value.get(0) : report for day 1
  // value.get(0).get(3) : report for day 1, 00:45 to 01:00(i.e. fourth 15 min duration)
  public List<List<TrafficReport>> reportPer15Minutes(){
    return dataRepository.reportForIntervals(15).getReports();
  }

  //Average of all the day
  public static TrafficReport averageOf(List<TrafficReport> reports){
    TrafficReport result = TrafficReport.emptyReport();
    for(TrafficReport report : reports){
      result = result.merge(report);
    }
    return result;
  }

  //Average of all the day for intervals
  public static List<TrafficReport> averageOfIntervals(List<List<TrafficReport>> reports){
    List<TrafficReport> result = new ArrayList<TrafficReport>();
    int intervals = reports.get(0).size();
    int totalDays = reports.size();
    for(int interval=0;interval< intervals;interval++){
      TrafficReport intervalReport = TrafficReport.emptyReport();
      for(int day = 0; day < totalDays; day++){
        intervalReport = intervalReport.merge(reports.get(day).get(interval));
      }
      result.add(interval, intervalReport);

    }
    return result;
  }

  // Duration must be in multiple of 5 minutes
  // returns a sorted map
  // returns the durations in order of traffic times
  // returned type maps a peak time starting point(in minutes) to average traffic in duration across all days.
  public Map<Integer, Long> durationsInOrderOfTraffic(int durationSizeInMinutes){
    List<TrafficReport> trafficReports = averageOfIntervals(dataRepository.reportForIntervals(durationSizeInMinutes).getReports());
    Map<Long, Integer> sortedWithIndex = trafficToIntervalMap(trafficReports);
    Map<Integer, Long> result = new LinkedHashMap<Integer, Long>();
    for(Long value : sortedWithIndex.keySet()){
      result.put(sortedWithIndex.get(value) * durationSizeInMinutes, value);
    }

    return result;
  }

  private Map<Long, Integer> trafficToIntervalMap(List<TrafficReport> trafficReports) {
    Map<Long, Integer> sortedByTraffic = new TreeMap<Long, Integer>();
    for(int i =0; i < trafficReports.size(); i++){
      sortedByTraffic.put(trafficReports.get(i).getVehicleCount(), i);
    }
    return sortedByTraffic;
  }

  // return ordered map of speed distributions
  public Map<Float, Long> speedDistribution(){
    List<VehicleData> vehicleDataList = dataRepository.unorderedAllVehicleData();
    Map<Float, Long> speedDistribution = new LinkedHashMap<Float, Long>();
    for(VehicleData vehicleData : vehicleDataList){
      addToMap(speedDistribution, vehicleData.getSpeed());
    }
    return speedDistribution;
  }

  private Long addToMap(Map<Float, Long> speedDistribution, float speed) {
    long count = 0;
    if(speedDistribution.containsKey(speed)){
      count = speedDistribution.get(speed);
    }
    return speedDistribution.put(speed, count+1);
  }

}
