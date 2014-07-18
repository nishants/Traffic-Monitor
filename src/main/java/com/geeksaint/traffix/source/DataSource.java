package com.geeksaint.traffix.source;

import com.geeksaint.traffix.Reading;

public interface DataSource {
  boolean hasNext();
  Reading getNext();
}
