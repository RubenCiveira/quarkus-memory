/* @autogenerated */
package org.acme.common.exception;

import java.util.List;
import org.acme.common.validation.AbstractFail;
import org.acme.common.validation.ConstraintFail;
import org.acme.common.validation.ConstraintFailList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConstraintExceptionUnitTest {

  @Test
  void test() {
    ConstraintException wa1 = new ConstraintException("c1", "d1", "s1");
    Assertions.assertTrue(wa1.hasErrors());
    Assertions.assertFalse(wa1.isEmpty());
    Assertions.assertTrue(wa1.includeCode("c1"));
    Assertions.assertFalse(wa1.includeCode("c2"));
    Assertions.assertTrue(wa1.includeViolation(ConstraintFail.class));
    Assertions.assertFalse(wa1.includeViolation(Mio.class));

    List<? extends AbstractFail> list =
        new ConstraintException("c1", "d1", "s1", "problem").getFails().toList();
    Assertions.assertEquals(1, list.size());
    Assertions.assertEquals("problem", list.get(0).getWrongValues().get(0).getErrorMessage());

    ConstraintException wa2 = new ConstraintException(new Mio("c1", "d1", "s1"));
    Assertions.assertTrue(wa2.includeViolation(Mio.class));

    ConstraintException wa3 =
        new ConstraintException(new ConstraintFailList(new Mio("c1", "d1", "s1")));
    Assertions.assertTrue(wa3.includeViolation(Mio.class));
  }
}


class Mio extends ConstraintFail {

  public Mio(String code, String field, Object wrongValue) {
    super(code, field, wrongValue);
  }

}
