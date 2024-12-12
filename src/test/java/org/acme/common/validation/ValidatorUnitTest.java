package org.acme.common.validation;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidatorUnitTest {

  @Test
  void test() {
    TestValidatorImpl t1 = new TestValidatorImpl("a");
    TestValidatorImpl t2 = new TestValidatorImpl("b");
    Validator<String> and = t1.and(t2);
    Validator<String> or = t1.or(t2);

    Assertions.assertFalse(and.validate("a").isValid());
    Assertions.assertFalse(and.validate("b").isValid());
    Assertions.assertFalse(and.validate("c").isValid());
    Assertions.assertTrue(and.validate("ab").isValid());

    Assertions.assertEquals(List.of("nop-b"), and.validate("a").getErrors());
    Assertions.assertEquals(List.of("nop-a"), and.validate("b").getErrors());
    Assertions.assertEquals(List.of("nop-a", "nop-b"), and.validate("c").getErrors());
    Assertions.assertTrue(and.validate("ab").getErrors().isEmpty());

    Assertions.assertTrue(or.validate("a").isValid());
    Assertions.assertTrue(or.validate("b").isValid());
    Assertions.assertFalse(or.validate("c").isValid());
    Assertions.assertTrue(or.validate("ab").isValid());

    Assertions.assertTrue(or.validate("a").getErrors().isEmpty());
    Assertions.assertTrue(or.validate("b").getErrors().isEmpty());
    Assertions.assertEquals(List.of("nop-a", "nop-b"), or.validate("c").getErrors());
    Assertions.assertTrue(and.validate("ab").getErrors().isEmpty());

    Validator<String> negate = t1.negate("cova");

    Assertions.assertFalse(negate.validate("a").isValid());
    Assertions.assertTrue(negate.validate("b").isValid());
    Assertions.assertEquals(List.of("cova"), negate.validate("a").getErrors());
    Assertions.assertTrue(negate.validate("b").getErrors().isEmpty());

  }

}


class TestValidatorImpl implements Validator<String> {
  private final String eq;

  public TestValidatorImpl(String eq) {
    this.eq = eq;
  }

  @Override
  public ValidationResult validate(String t) {
    return t.contains(eq) ? new ValidationResult() : new ValidationResult("nop-" + eq);
  }
}
