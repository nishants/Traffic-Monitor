package com.geeksaint.traffix.analysis;

import com.geeksaint.traffix.DurationReport;
import com.geeksaint.traffix.IntervalReport;
import com.geeksaint.traffix.maker.DurationReportMaker;
import com.geeksaint.traffix.maker.IntervalReportMaker;
import com.geeksaint.traffix.persist.DataRepository;
import com.geeksaint.traffix.persist.TrafficReport;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.geeksaint.traffix.Lane.LANE_A;
import static com.geeksaint.traffix.Lane.LANE_B;
import static com.geeksaint.traffix.analysis.Analyzer.EVENING_END_TIME_IN_MINUTES;
import static com.geeksaint.traffix.analysis.Analyzer.EVENING_START_TIME_IN_MINUTES;
import static com.geeksaint.traffix.analysis.Analyzer.MORNING_END_TIME_IN_MINUTES;
import static com.geeksaint.traffix.analysis.Analyzer.MORNING_START_TIME_IN_MINUTES;
import static com.geeksaint.traffix.maker.TrafficReportMaker.aReportFor;
import static com.geeksaint.traffix.maker.VehicleDataMaker.vehicleWith;
import static com.geeksaint.traffix.util.DateSupport.timeStampToMillis;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static java.util.Arrays.asList;
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

  @Test
  public void shouldCalculateAverageOfReports(){
    TrafficReport reportOne = aReportFor(vehicleWith(100l, 39f, LANE_B));
    TrafficReport reportTwo = aReportFor(vehicleWith(200l, 39f, LANE_B));
    TrafficReport reportThree = aReportFor(vehicleWith(300l, 39f, LANE_B));

    TrafficReport expectedReport = reportOne.merge(reportTwo).merge(reportThree);

    assertThat(Analyzer.averageOf(asList(reportOne, reportTwo, reportThree)), is(expectedReport));
  }

  @Test
  public void shouldCalculateAverageOfIntervalReports(){
    TrafficReport dayOneReportOne = aReportFor(vehicleWith(100l, 39f, LANE_B));
    TrafficReport dayOneReportTwo = aReportFor(vehicleWith(200l, 39f, LANE_B));
    TrafficReport dayOneReportThree = aReportFor(vehicleWith(300l, 39f, LANE_B));

    TrafficReport dayTwoReportOne = aReportFor(vehicleWith(400l, 39f, LANE_B));
    TrafficReport dayTwoReportTwo = aReportFor(vehicleWith(500l, 39f, LANE_B));
    TrafficReport dayTwoReportThree = aReportFor(vehicleWith(600l, 39f, LANE_B));

    TrafficReport dayThreeReportOne = aReportFor(vehicleWith(700l, 39f, LANE_B));
    TrafficReport dayThreeReportTwo = aReportFor(vehicleWith(100l, 39f, LANE_B));
    TrafficReport dayThreeReportThree = aReportFor(vehicleWith(100l, 39f, LANE_B));

    List<TrafficReport> dayOneReport   = asList(dayOneReportOne,   dayOneReportTwo,   dayOneReportThree);
    List<TrafficReport> dayTwoReport   = asList(dayTwoReportOne,   dayTwoReportTwo,   dayTwoReportThree);
    List<TrafficReport> dayThreeReport = asList(dayThreeReportOne, dayThreeReportTwo, dayThreeReportThree);

    List<List<TrafficReport>> intervalReport = asList(
        dayOneReport,
        dayTwoReport,
        dayThreeReport
    );

    List<TrafficReport> expected = asList(
        dayOneReportOne.merge(dayTwoReportOne).merge(dayThreeReportOne),
        dayOneReportTwo.merge(dayTwoReportTwo).merge(dayThreeReportTwo),
        dayOneReportThree.merge(dayTwoReportThree).merge(dayThreeReportThree)
    );
    assertThat(Analyzer.averageOfIntervals(intervalReport), is(expected));
  }

  @Test
  public void shouldCalculatePeakTimes(){
    List<TrafficReport> reportForDayOne = asList(
        aReportFor(
            vehicleWith(timeStampToMillis("0221"), 20f, LANE_A)
            ),
        aReportFor(
            vehicleWith(timeStampToMillis("0821"), 20f, LANE_A),
            vehicleWith(timeStampToMillis("0831"), 20f, LANE_A),
            vehicleWith(timeStampToMillis("0901"), 20f, LANE_A)
        ),
        aReportFor(
            vehicleWith(timeStampToMillis("1421"), 20f, LANE_A),
            vehicleWith(timeStampToMillis("1422"), 20f, LANE_A)
        ),
        aReportFor(
            vehicleWith(timeStampToMillis("1821"), 20f, LANE_A),
            vehicleWith(timeStampToMillis("1821"), 20f, LANE_A),
            vehicleWith(timeStampToMillis("1921"), 20f, LANE_A),
            vehicleWith(timeStampToMillis("1921"), 20f, LANE_A)
        )
    );

    IntervalReport report = mock(IntervalReport.class);
    when(report.getReports()).thenReturn(asList(reportForDayOne));

    int peakPeriodSize = 6*60;
    when(repository.reportForIntervals(peakPeriodSize)).thenReturn(report);

    Map<Integer, Long> expected = new LinkedHashMap<Integer, Long>();
    expected.put(18 * 60, 4l);
    expected.put(6 * 60, 3l);
    expected.put(12 * 60, 2l);
    expected.put(0, 1l);
    assertThat(analyzer.durationsInOrderOfTraffic(peakPeriodSize), is(expected));

  }
}
