package org.acme.common.validation;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidationResultUnitTest {

  @Test
  void test() {
    ValidationResult v1 = new ValidationResult();
    Assertions.assertTrue(v1.isValid());
    Assertions.assertTrue(v1.getErrors().isEmpty());

    ValidationResult v2 = new ValidationResult("gollo");
    Assertions.assertFalse(v2.isValid());
    Assertions.assertEquals("gollo", v2.getErrors().get(0));

    ValidationResult v3 = new ValidationResult(false, List.of("uno", "dos"));
    Assertions.assertFalse(v3.isValid());
    Assertions.assertEquals("uno", v3.getErrors().get(0));
    Assertions.assertEquals("dos", v3.getErrors().get(1));
  }
}
