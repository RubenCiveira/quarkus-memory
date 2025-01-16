package org.acme.features.market.medal.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MedalReferenceUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_medal_reference_builder() {
    MedalReference ref = MedalReference.of("one");
    Assertions.assertEquals("one", ref.getUid().getValue());
  }
}
