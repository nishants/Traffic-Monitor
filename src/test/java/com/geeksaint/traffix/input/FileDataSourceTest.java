package com.geeksaint.traffix.input;

import com.geeksaint.traffix.interpret.Reading;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.geeksaint.traffix.interpret.Reading.*;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FileDataSourceTest {
  @Test
  public void shouldReadFile() {
    FileDataSource reader = new FileDataSource(1970, 1, 1, recordFile("/data/test_data_reader.txt"));
    List<Reading> readList = readUsing(reader);

    Date recordingDate = toDate(1970, 1, 1);
    List<Reading> expectedList = asList(
        of(addToDate(recordingDate, 268981l), true),
        of(addToDate(recordingDate, 269123l), true),
        of(addToDate(recordingDate, 604957l), true),
        of(addToDate(recordingDate, 604960l), false),
        of(addToDate(recordingDate, 605128l), true),
        of(addToDate(recordingDate, 605132l), false),
        of(addToDate(recordingDate, 1089807l), true),
        of(addToDate(recordingDate, 1089810l), false),
        of(addToDate(recordingDate, 1089948l), true),
        of(addToDate(recordingDate, 1089951l), false),
        of(addToDate(increment(recordingDate), 100l), true),
        of(addToDate(increment(increment(recordingDate)), 20l), true)
    );

    assertThat(readList, is(expectedList));
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

  private List<Reading> readUsing(FileDataSource reader) {
    List<Reading> readList = new ArrayList<Reading>();
    while (reader.hasNext()) {
      readList.add(reader.getNext());
    }
    return readList;
  }
}
