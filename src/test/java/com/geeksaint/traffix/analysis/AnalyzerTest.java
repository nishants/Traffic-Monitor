package com.geeksaint.traffix.analysis;

import com.geeksaint.traffix.DurationReport;
import com.geeksaint.traffix.IntervalReport;
import com.geeksaint.traffix.maker.DurationReportMaker;
import com.geeksaint.traffix.maker.IntervalReportMaker;
import com.geeksaint.traffix.persist.DataRepository;
import com.geeksaint.traffix.persist.TrafficReport;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.geeksaint.traffix.analysis.Analyzer.EVENING_END_TIME_IN_MINUTES;
import static com.geeksaint.traffix.analysis.Analyzer.EVENING_START_TIME_IN_MINUTES;
import static com.geeksaint.traffix.analysis.Analyzer.MORNING_END_TIME_IN_MINUTES;
import static com.geeksaint.traffix.analysis.Analyzer.MORNING_START_TIME_IN_MINUTES;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnalyzerTest {

  private DataRepository repository;
  private Analyzer analyzer;
  private DurationReport aDurationReport;
  private IntervalReport anIntervalReport;

  @Before
  public void setUp() throws Exception {
    repository = mock(DataRepository.class);
    analyzer = new Analyzer(repository);
    aDurationReport = make(a(DurationReportMaker.DurationReport));
    anIntervalReport = make(a(IntervalReportMaker.IntervalReport));
  }

  @Test
  public void shouldGenerateMorningReport(){
    when(repository.reportForDuration(
        MORNING_START_TIME_IN_MINUTES,
        MORNING_END_TIME_IN_MINUTES)
    ).thenReturn(aDurationReport);

    List<TrafficReport> expectedReports = aDurationReport.getReports();

    assertThat(analyzer.morningTraffic(), is(expectedReports));
  }

  @Test
  public void shouldGenerateEveningReport(){
    when(repository.reportForDuration(
        EVENING_START_TIME_IN_MINUTES,
        EVENING_END_TIME_IN_MINUTES)
    ).thenReturn(aDurationReport);

    List<TrafficReport> expectedReports = aDurationReport.getReports();

    assertThat(analyzer.eveningTraffic(), is(expectedReports));
  }

  @Test
  public void shouldGenerateHourlyReport(){
    int minutesInAnHour = 60;
    when(repository.reportForIntervals(minutesInAnHour)).thenReturn(anIntervalReport);

    List<List<TrafficReport>> expectedReports = anIntervalReport.getReports();

    assertThat(analyzer.hourlyTrafficReport(), is(expectedReports));
  }

  @Test
  public void shouldGenerateHalfHourlyReport(){
    int minutesInHalfHour = 30;
    when(repository.reportForIntervals(minutesInHalfHour)).thenReturn(anIntervalReport);

    List<List<TrafficReport>> expectedReports = anIntervalReport.getReports();

    assertThat(analyzer.halfHourlyTrafficReport(), is(expectedReports));
  }

  @Test
  public void shouldGenerate20MinsReport(){
    when(repository.reportForIntervals(20)).thenReturn(anIntervalReport);

    List<List<TrafficReport>> expectedReports = anIntervalReport.getReports();

    assertThat(analyzer.reportPer20Minutes(), is(expectedReports));
  }

  @Test
  public void shouldGenerate15MinsReport(){
    when(repository.reportForIntervals(15)).thenReturn(anIntervalReport);

    List<List<TrafficReport>> expectedReports = anIntervalReport.getReports();

    assertThat(analyzer.reportPer15Minutes(), is(expectedReports));
  }

}
