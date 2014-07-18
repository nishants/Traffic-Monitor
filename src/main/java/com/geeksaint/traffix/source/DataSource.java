package com.geeksaint.traffix.source;

import com.geeksaint.traffix.interpret.Reading;

public interface DataSource {
  boolean hasNext();
  Reading getNext();
}
