package com.geeksaint.traffix;

import com.geeksaint.traffix.input.FileDataSource;
import com.geeksaint.traffix.input.DataSource;
import com.geeksaint.traffix.interpret.VehicleDataInterpreter;
import com.geeksaint.traffix.maker.ReadingMaker;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.geeksaint.traffix.Lane.*;
import static com.geeksaint.traffix.VehicleData.*;
import static com.geeksaint.traffix.interpret.Reading.of;
import static com.geeksaint.traffix.maker.ReadingMaker.makeReading;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TraffixTest {
  @Test
  public void shouldReadDataAndConvertToVehicleData() {
    Date recordingDate = toDate(1970, 1, 1);
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
            makeReading(increment(recordingDate), 1089948l, LANE_A),
            makeReading(increment(recordingDate), 1089951l, LANE_A)
        ))
    );
    assertThat(vehicleDataList.size(), is(7));
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

  private Date toDate(int day, int month, int year) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    return calendar.getTime();
  }

  private Date addToDate(Date date, long timeInMillis) {
    return new Date(date.getTime() + timeInMillis);
  }

  private Date increment(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(date.getTime());
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    return calendar.getTime();
  }

  private InputStream recordFile(String fileName) {
    return getClass().getResourceAsStream(fileName);
  }
}
