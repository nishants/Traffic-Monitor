package com.geeksaint.traffix.interpret;

import com.geeksaint.traffix.Reading;

public class UnexpectedReadingException extends RuntimeException{
  public static final String EXPECTED_HOSE_A_READING = "Expected hose A reading, found hose B";
  public static final String EXPECTED_HOSE_B_READING = "Expected hose B reading, found hose A";

  public UnexpectedReadingException(String message) {
    super(message);
  }

  public static void checkForHoseA(Reading reading){
    if(!reading.isHoseA()) throw new UnexpectedReadingException(EXPECTED_HOSE_A_READING);
  }
  public static void checkForHoseB(Reading reading){
    if(reading.isHoseA()) throw new UnexpectedReadingException(EXPECTED_HOSE_B_READING);
  }
}
