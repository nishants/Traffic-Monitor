package com.geeksaint.traffix.source;

import com.geeksaint.traffix.Lane;
import com.geeksaint.traffix.interpret.Reading;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static java.lang.Long.*;

//Represent a reading source backed by a file
public class FileDataSource implements DataSource {
  private final Scanner scanner;
  private Date currentDayOfRecording;
  private long lastRecordingTime;

  public FileDataSource(int day, int month, int year, InputStream inputStream) {
    currentDayOfRecording = toDate(day, month, year);
    scanner = new Scanner(inputStream);
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

  @Override
  public boolean hasNext() {
    return scanner.hasNext();
  }

  @Override
  public Reading getNext() {
    return toReading(scanner.next());
  }

  // Converts the time part of Strings like "A1242" or "B3848" to time,
  // combining with the start date.
  private Reading toReading(String token) {
    return Reading.of(
        currentDateOf(token),
        Lane.of(token)
    );
  }

  private Date currentDateOf(String token) {
    long time = parseLong(token.substring(1));
    if (lastRecordingTime > time) incrementCurrentDayOfRecording();
    lastRecordingTime = time;
    return new Date(currentDayOfRecording.getTime() + time);
  }

  private void incrementCurrentDayOfRecording() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(currentDayOfRecording.getTime());
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    currentDayOfRecording = calendar.getTime();
  }
}
