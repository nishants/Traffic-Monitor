package com.geeksaint.traffix.analysis;

import com.geeksaint.traffix.VehicleData;
import com.geeksaint.traffix.maker.VehicleDataMaker;
import com.geeksaint.traffix.persist.VehicleDataRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static com.geeksaint.traffix.maker.TrafficReportMaker.*;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrafficAnalyzerTest {

  private TrafficAnalyzer analyzer;
  private VehicleDataRepository repository;

  @Before
  public void setUp() throws Exception {
    repository = mock(VehicleDataRepository.class);
    analyzer = new TrafficAnalyzer(repository);
  }

  @Test
  public void shouldPrepareTrafficReport(){
    Date formTime = new Date(100l);
    Date toTime = new Date(1000l);
    List<VehicleData> vehicleDataList = asList(
        make(a(VehicleDataMaker.VehicleData, with(VehicleDataMaker.time, 105l))),
        make(a(VehicleDataMaker.VehicleData, with(VehicleDataMaker.time, 306l))),
        make(a(VehicleDataMaker.VehicleData, with(VehicleDataMaker.time, 505l)))
    );
    when(repository.getAllVehiclesByTime(formTime, toTime)).thenReturn(vehicleDataList);
    TrafficReport expectedReport = make(a(Report, with(vehicleData, vehicleDataList)));

    assertThat(analyzer.getTrafficReport(formTime, toTime), is(expectedReport));
  }

}
