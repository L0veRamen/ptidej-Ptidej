package com.sdmetrics.metrics;

import static org.junit.Assert.assertEquals;

import com.sdmetrics.model.ModelElement;
import com.sdmetrics.test.MetricTestContext;
import java.util.HashSet;
import org.junit.Test;

public class TestSetProcedureCompound {

  @Test
  public void compound() throws Exception {
    MetricTestContext mtc = MetricTestContext.getStandardContext04();
    HashSet<ModelElement> result = new HashSet<ModelElement>();
    result.add(mtc.getElement("Resource", "class"));
    result.add(mtc.getElement("Supplier", "class"));
    assertEquals(result, mtc.getSetValue("DependingClassesTC", "Manufacturer", "class"));
  }
}
