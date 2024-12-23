package org.acme.features.market.fruit.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FruitReferenceUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_fruit_reference_builder() {
    FruitReference ref = FruitReference.of("one");
    Assertions.assertEquals("one", ref.getUid().getValue());
  }
}
