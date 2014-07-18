package com.geeksaint.traffix.source;

import com.geeksaint.traffix.Lane;
import com.geeksaint.traffix.Reading;

import java.io.InputStream;
import java.util.Date;
import java.util.Scanner;

import static com.geeksaint.traffix.util.DateSupport.addDaysTo;
import static com.geeksaint.traffix.util.DateSupport.toDateOfYear;
import static java.lang.Long.*;

//Represent a reading source backed by a file
public class FileDataSource implements DataSource {
  private final Scanner scanner;
  private Date currentDayOfRecording;
  private long lastRecordingTime;

  public FileDataSource(int day, int month, int year, InputStream inputStream) {
    currentDayOfRecording = toDateOfYear(day, month, year);
    scanner = new Scanner(inputStream);
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
    if (lastRecordingTime > time){
      currentDayOfRecording = addDaysTo(currentDayOfRecording, 1);
    }
    lastRecordingTime = time;
    return new Date(currentDayOfRecording.getTime() + time);
  }

}
