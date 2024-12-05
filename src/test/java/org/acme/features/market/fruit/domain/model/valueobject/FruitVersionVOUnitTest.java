package org.acme.features.market.fruit.domain.model.valueobject;

import org.acme.common.exception.ConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FruitVersionVOUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test value object contruction for property version of fruit ")
  void test_fruit_version_v_o_builder() {
    Assertions.assertFalse(FruitVersionVO.empty().getValue().isPresent(),
        "A empty vo should have a null value");
    Assertions.assertFalse(FruitVersionVO.tryFrom(null).getValue().isPresent());
    Assertions.assertEquals(1, FruitVersionVO.from(1).getValue().orElse(null));
    Assertions.assertThrows(ConstraintException.class,
        () -> FruitVersionVO.tryFrom(new Object() {}));
  }
}