package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.VehicleData;
import com.geeksaint.traffix.input.DataSource;
import org.junit.Test;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.geeksaint.traffix.interpret.Reading.of;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class VehicleDataInterpreterTest {
  @Test
  public void takesReadingsFromSourceAndReturnsVehicle() {
    List<Reading> readingList = asList(
        of((new Date(268981l)), true),
        of((new Date(268981l)), true),
        of((new Date(268981l)), true),
        of((new Date(268981l)), true)
    );
    DataSource dataSource = mockeFor(readingList);

    VehicleData expectedOne = VehicleData.record(readingList.subList(0, 2));
    VehicleData expectedTwo = VehicleData.record(readingList.subList(2, 4));

    VehicleDataInterpreter interpreter = VehicleDataInterpreter.load(dataSource);

    assertThat(interpreter.hasNext(), is(true));
    assertThat(interpreter.next(), is(expectedOne));
    assertThat(interpreter.next(), is(expectedTwo));
  }

  private DataSource mockeFor(List<Reading> readingList) {
    final Iterator<Reading> iterator = readingList.iterator();
    DataSource dataSource = new DataSource() {
      @Override
      public boolean hasNext() {
        return iterator.hasNext();
      }
      @Override
      public Reading getNext() {
        return iterator.next();
      }
    };
    return dataSource;
  }
}
