package org.acme.common.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConstraintFailUnitTest {

  @Test
  void test() {
    ConstraintFail wa1 = new ConstraintFail("c1", "d1", "s1");
    Assertions.assertEquals(1, wa1.getWrongValues().size());

    ConstraintFail wa2 = new ConstraintFail("c1", "d1", "s1", "message");
    Assertions.assertEquals(1, wa2.getWrongValues().size());
    Assertions.assertEquals("message", wa2.getWrongValues().get(0).getErrorMessage());
  }
}
