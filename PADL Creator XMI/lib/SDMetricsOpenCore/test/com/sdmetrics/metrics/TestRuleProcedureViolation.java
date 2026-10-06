package com.sdmetrics.metrics;

import com.sdmetrics.test.MetricTestContext;
import org.junit.Test;

public class TestRuleProcedureViolation {

  @Test
  public void violation() throws Exception {
    MetricTestContext mtc = MetricTestContext.getStandardContext04();

    mtc.assertViolation("Manufacturer", "NameTooLong", "class", "Manufacturer");
    mtc.assertCompliance("Car", "NameTooLong", "class");
  }
}
