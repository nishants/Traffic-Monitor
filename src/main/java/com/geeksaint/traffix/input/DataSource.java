package com.geeksaint.traffix.input;

import com.geeksaint.traffix.interpret.Reading;

public interface DataSource {
  boolean hasNext();
  Reading getNext();
}
