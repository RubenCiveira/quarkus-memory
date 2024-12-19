package org.acme.features.market.color.domain.model.valueobject;

import org.acme.common.exception.ConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ColorUidVOUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test value object contruction for property uid of color ")
  void test_color_uid_v_o_builder() {
    Assertions.assertThrows(ConstraintException.class, () -> ColorUidVO.tryFrom(null));
    Assertions.assertEquals("one", ColorUidVO.from("one").getValue());
    Assertions.assertThrows(ConstraintException.class, () -> ColorUidVO.tryFrom(new Object() {}));
  }
}