package com.geeksaint.traffix;

import com.geeksaint.traffix.persist.TrafficReport;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
//Represents a report like 08:00 to 10:10 for all days
public class DurationReport {
  private final Date sessionStartTime;
  private final List<TrafficReport> reports;

  public TrafficReport getForDay(int day){
    return reports.get(day);
  }
  public static DurationReport prepare(Date sessionStartTime, List<TrafficReport> reports){
    return new DurationReport(sessionStartTime, reports);
  }
}
