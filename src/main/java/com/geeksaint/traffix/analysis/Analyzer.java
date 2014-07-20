package com.geeksaint.traffix.analysis;

import com.geeksaint.traffix.persist.DataRepository;
import com.geeksaint.traffix.persist.TrafficReport;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

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
    return null;
  }

  //Average of all the day for intervals
  public static List<TrafficReport> averageOfIntervals(List<List<TrafficReport>> reports){
    return null;
  }

  //Duration must be in multiple of 5 minutes
  // returns the durations in order of traffic times
  // returned type maps a peak time starting point(in minutes) to average traffic in duration across all days.
  public Map<Integer, Long> peakTimes(int durationSize){
    return null;
  }

  // maps  a speeding range of 5ps to the number of vehicles at the speed.
  // e.g 0-5mps: 25, 5-10mps : 30
  public Map<Integer, Long> speedDistribution(int durationSize){
    return null;
  }

}
