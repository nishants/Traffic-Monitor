package com.geeksaint.traffix;

import com.geeksaint.traffix.persist.TrafficReport;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
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

  public List<TrafficReport> getForDay(int day) {
    return reports.get(day);
  }

  public static IntervalReport prepareFrom(List<DurationReport> durationReports) {
    List<List<TrafficReport>> reports = new ArrayList<List<TrafficReport>>();

    for (int currentDay = 0; currentDay < durationReports.get(0).getReports().size(); currentDay++) {
      List<TrafficReport> values = new ArrayList<TrafficReport>();
      for (int interval = 0; interval < durationReports.size(); interval++) {
        values.add(durationReports.get(interval).getReports().get(currentDay));
      }
      reports.add(currentDay, values);
    }

    return new IntervalReport(durationReports.get(0).getSessionStartTime(), reports);
  }
}
