package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;

import com.sdmetrics.test.MetricTestContext;
import org.junit.Test;

public class TestMetricProcedureNesting {

  @Test
  public void nesting() throws Exception {
    MetricTestContext mtc = MetricTestContext.getStandardContext04();
    assertEquals(3, mtc.getMetricValue("Nesting", "package1_1_1", "package"), 0);
    assertEquals(0, mtc.getMetricValue("Nesting", "scope", "package"), 0);
    assertEquals(0, mtc.getMetricValue("Nesting", "testModel04", "model"), 0);
  }
}
