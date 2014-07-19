package com.geeksaint.traffix;

import com.geeksaint.traffix.source.FileDataSource;
import com.geeksaint.traffix.source.DataSource;
import com.geeksaint.traffix.interpret.VehicleDataInterpreter;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.geeksaint.traffix.Lane.*;
import static com.geeksaint.traffix.VehicleData.*;
import static com.geeksaint.traffix.maker.ReadingMaker.makeReading;
import static com.geeksaint.traffix.util.DateSupport.increment;
import static com.geeksaint.traffix.util.DateSupport.toDateOfYear;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TraffixTest {
  @Test
  public void shouldReadDataAndConvertToVehicleData() {
    Date recordingDate = toDateOfYear(1970, 1, 1);
    DataSource dataSource = new FileDataSource(1970, 1, 1, recordFile("/data/sample_data.txt"));
    VehicleDataInterpreter interpreter = VehicleDataInterpreter.load(dataSource);
    List<VehicleData> vehicleDataList = collectDataUsing(interpreter);

    List<VehicleData> expectedList = asList(
        record(asList(
            makeReading(recordingDate, 268981l, LANE_A),
            makeReading(recordingDate, 269123l, LANE_A)
        )),

        record(asList(
            makeReading(recordingDate, 604957l, LANE_A),
            makeReading(recordingDate, 604960l, LANE_A)
        )),

        record(asList(
            makeReading(recordingDate, 605128l, LANE_A),
            makeReading(recordingDate, 605132l, LANE_A)
        )),

        record(asList(
            makeReading(recordingDate, 1089807l, LANE_A),
            makeReading(recordingDate, 1089810l, LANE_A)
        )),
        record(asList(
            makeReading(recordingDate, 1089948l, LANE_A),
            makeReading(recordingDate, 1089951l, LANE_A)
        )),
        record(asList(
            makeReading(increment(recordingDate), 100l, LANE_A),
            makeReading(increment(recordingDate), 1200, LANE_A)
        )),
        record(asList(
            makeReading(increment(recordingDate), 268981l, LANE_A),
            makeReading(increment(recordingDate), 269123l, LANE_B),
            makeReading(increment(recordingDate), 604957l, LANE_A),
            makeReading(increment(recordingDate), 604960l, LANE_B)
        )),
        record(asList(
            makeReading(increment(recordingDate), 605128l, LANE_A),
            makeReading(increment(recordingDate), 605132l, LANE_A)
        )),
        record(asList(
            makeReading(increment(recordingDate), 1089807l, LANE_A),
            makeReading(increment(recordingDate), 1089810l, LANE_B),
            makeReading(increment(recordingDate), 1089948l, LANE_A),
            makeReading(increment(recordingDate), 1089951l, LANE_B)

        ))
    );
    assertThat(vehicleDataList.size(), is(9));
    assertThat(vehicleDataList, is(expectedList));
  }

  private List<VehicleData> collectDataUsing(VehicleDataInterpreter interpreter) {
    List<VehicleData> vehicleDataList = new ArrayList<VehicleData>();
    while (interpreter.hasNext()) {
      vehicleDataList.add(interpreter.next()
      );
    }
    return vehicleDataList;
  }

  private InputStream recordFile(String fileName) {
    return getClass().getResourceAsStream(fileName);
  }
}
