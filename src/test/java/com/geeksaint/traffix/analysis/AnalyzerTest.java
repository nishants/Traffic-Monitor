package com.geeksaint.traffix.analysis;

import com.geeksaint.traffix.persist.DataRepository;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class AnalyzerTest {

  private DataRepository repository;
  private Analyzer analyzer;

  @Before
  public void setUp() throws Exception {
    repository = mock(DataRepository.class);
    analyzer = new Analyzer(repository);
  }

  @Test
  public void shouldGenerateMorningReport(){
  }
}
