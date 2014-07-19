package com.geeksaint.traffix;

import com.geeksaint.traffix.persist.TrafficReport;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

//Represents a report like per 5 minutes everyday
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class IntervalReport {
  private final Date sessionStartTime;
  private final List<List<TrafficReport>> reports;

  public List<TrafficReport> getForDay(int day){ return  null;}
  public static IntervalReport prepare(Date sessionStartTime, List<List<TrafficReport>> reports){
    return new IntervalReport(sessionStartTime, reports);
  }
}
